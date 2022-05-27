(ns pedestal-hug-sql.sql
  (:require [hugsql.core :as hugsql]))

(hugsql/def-db-fns "pedestal_hug_sql/sql/festival.sql")