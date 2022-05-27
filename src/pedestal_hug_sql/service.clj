(ns pedestal-hug-sql.service
  (:require [io.pedestal.http :as http]
            [io.pedestal.interceptor :as interceptor]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [ring.util.response :as ring-resp]
            [cheshire.core :as json]
            [io.pedestal.log :as log]
            [pedestal-hug-sql.db :refer [db]]
            [pedestal-hug-sql.sql :as sql]))

(def common-interceptors [http/json-body])

(def article-by-id
  {:name :article-by-id
   :enter (fn [context]
            (print "Hello")
            (if-let [artist-id (get-in context [:request :path-params :artist-id])]
              (do
                (log/debug :artist-id artist-id)
                (ring-resp/response (sql/artist-by-id db {:id (java.util.UUID/fromString artist-id)})))
              context))})


;; (def list-item-view
;;   {:name :list-item-view
;;    :leave (fn [context]
;;             (if-let [list-id (get-in context [:request :path-params :list-id])]
;;               (if-let [item-id (get-in context [:request :path-params :item-id])]
;;                 (if-let [item (find-list-item-by-ids (get-in context [:request :database]) list-id item-id)]
;;                   (assoc context :result item)
;;                   context)
;;                 context)
;;               context))})

(defn all-articles
  [request]
  (ring-resp/response (sql/artists-all db)))

;; Tabular routes
(def routes #{["/artists" :get (conj common-interceptors `all-articles)]

              ["/artist" :get [article-by-id]]
              ;; ["/artist" :post (conj common-interceptors `all-articles)]
              ;; ["/artist/:artist-id" :patch (conj common-interceptors `all-articles)]
              ;; ["/artist/:artist-id" :delete (conj common-interceptors `all-articles)]
              })



(def service {:env :prod
              ::http/routes routes
              ::http/resource-path "/public"
              ::http/type :jetty
              ::http/port 8080
              ::http/container-options {:h2c? true
                                        :h2? false
                                        :ssl? false}})