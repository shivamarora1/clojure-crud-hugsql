(ns pedestal-hug-sql.service
  (:require [io.pedestal.http :as http]
            [io.pedestal.interceptor :as interceptor]
            [io.pedestal.interceptor.helpers :refer [handler]]
            [io.pedestal.interceptor.error :as error-int]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [ring.util.response :as ring-resp]
            [cheshire.core :as json]
            [clojure.spec.alpha :as s]
            [clojure.stacktrace :as trace]
            [io.pedestal.log :as log]
            [pedestal-hug-sql.db :refer [db]]
            [pedestal-hug-sql.sql :as sql]))

;; specs definition
(def uuid-regex #"^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$")

(s/def :artist/id (s/and string? #(re-matches uuid-regex %)))
(s/def :artist/name string?)
(s/def :artist/date string?);; can use date time data type with this

(s/def :artist/artist (s/keys :req-un [:artist/name]
                              :opt-un [:artist/id :artist/date]))
;; end of specs definition

;; error handler
(def common-error-handler
  (error-int/error-dispatch [ctx ex]
                            [{:interceptor :article-by-id-request-validator}]
                            (assoc ctx :response (-> (ring.util.response/response "invalid input given")) :status 400)
                            :else
                            (assoc ctx :io.pedestal.impl.interceptor/error ex)))

(def common-interceptors [common-error-handler http/json-body])

;;; validators
(def article-by-id-request-validator
  {:name :article-by-id-request-validator
   :enter (fn [{:keys [request] :as context}]
            (let [artist-id (-> request
                                :path-params
                                :artist-id)]
              (if (s/valid? :artist/id artist-id)
                context
                (throw "artist-id param not found"))))})
;;;

(def article-by-id
  {:name :article-by-id
   :enter (fn [{:keys [request] :as context}]
            (let [artist-id (-> request
                                :path-params
                                :artist-id)]
              (as-> artist-id v
                (java.util.UUID/fromString v)
                (sql/artist-by-id db {:id v})
                (ring-resp/response v)
                (assoc context :response v))))})

(defn all-articles
  [request]
  (ring-resp/response (sql/artists-all db)))

;; Tabular routes
(def routes
  (route/expand-routes
   #{["/artists" :get (conj common-interceptors `all-articles)]
     ["/artist/:artist-id" :get (conj common-interceptors `article-by-id-request-validator `article-by-id)]
              ;; ["/artist" :post (conj common-interceptors `all-articles)]
              ;; ["/artist/:artist-id" :patch (conj common-interceptors `all-articles)]
              ;; ["/artist/:artist-id" :delete (conj common-interceptors `all-articles)]
     }))

;; :constraints {:artist-id #"^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"}

(def service {:env :prod
              ::http/routes routes
              ::http/resource-path "/public"
              ::http/type :jetty
              ::http/port 8080
              ::http/container-options {:h2c? true
                                        :h2? false
                                        :ssl? false}})