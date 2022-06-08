# pedestal-hug-sql

The project shows how we can make a rest api web service using Pedestal framework and Hug Sql.
Currently this project has only two working endpoints.
1. `/artists` : To get information about all the artists available in the table.
2. `/artists/:artist-id` : To fetch the information of a particular artist.


## How to run this project:
1. Make `.env` file in the root directory.
   ```
   DATABASE_SUB_NAME="//{{SQL_DATABASE_CURL}}:5432/{{SQL_DATABASE_NAME}}"
   DATABASE_USER_NAME="{{SQL_DATABASE_USERNAME}}"
   DATABASE_PASSWORD="{{SQL_DATABASE_PASSWORD}}"
   ```
2. Start the application `lein run`.

## Things to figure out
1. Print exceptions neatly
2. Mocking/ testing
