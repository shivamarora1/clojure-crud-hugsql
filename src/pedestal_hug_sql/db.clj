(ns pedestal-hug-sql.db
  (:require [dotenv :refer [env]]))

(def db
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname (env "DATABASE_SUB_NAME")
   :user (env "DATABASE_USER_NAME")
   :password (env "DATABASE_PASSWORD")
   :sslmode "require"})