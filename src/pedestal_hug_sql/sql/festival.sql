-- :name artists-all :? :*
-- :command :query
-- :doc select all the artists with all the attributes
SELECT *  
  FROM artists

-- :name artists :? :*
-- :command :query
-- :doc select all with cols defined by {:cols [<col_name>...]}
SELECT :i*:cols  
  FROM artists

-- Note the terse style below
-- ":command :query" -> ":?"
-- ":result n" -> ":*" 

-- :name artist-by-id :? :*
-- :doc get artist info by music_brainz id
SELECT *  
  FROM artists
 WHERE mb_id = :id

-- :name artists-by-name-like :? :*
-- :doc use {:name-like "P%"} as the option param to get the P's
SELECT *  
  FROM artists
 WHERE name 
  LIKE :name-like

-- :name new-rating :! :n
-- :doc insert new ratings
INSERT INTO ratings (artist_name, email, rating_type_name, rating)  
VALUES (:artist_name, :email, :rating_type_name, :rating)

-- :name new-rating-type :! :n
-- :doc insert new rating-types
INSERT INTO rating_types (name, description)  
VALUES (:name, :description)