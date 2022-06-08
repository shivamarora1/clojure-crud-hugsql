(defproject pedestal-hug-sql "0.0.1-SNAPSHOT"
  :description "Pedestal example with HugSQL"
  :url "https://github.com/shivamarora1?tab=repositories"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [io.pedestal/pedestal.service "0.5.10"]
                 [io.pedestal/pedestal.jetty "0.5.10"]
                 [ch.qos.logback/logback-classic "1.2.3" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.26"]
                 [org.slf4j/jcl-over-slf4j "1.7.26"]
                 [org.slf4j/log4j-over-slf4j "1.7.26"]
                 [io.pedestal/pedestal.log "0.5.10"]
                 [com.layerware/hugsql "0.5.3"]
                 [ring/ring-json "0.1.2"]
                 [com.cemerick/url "0.1.1"]
                 [lynxeyes/dotenv "1.1.0"]
                 [org.postgresql/postgresql "42.3.1"]]
  :min-lein-version "2.0.0"
  :resource-paths ["config", "resources"]
  :profiles {:dev {:aliases {"run-dev" ["trampoline" "run" "-m" "pedestal-hug-sql.server/run-dev"]}
                   :dependencies [[io.pedestal/pedestal.service-tools "0.5.10"]]}
             :uberjar {:aot [pedestal-hug-sql.server]}}
  :main ^{:skip-aot true} pedestal-hug-sql.server)
