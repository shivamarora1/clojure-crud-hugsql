FROM openjdk:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/pedestal-hug-sql-0.0.1-SNAPSHOT-standalone.jar /pedestal-hug-sql/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/pedestal-hug-sql/app.jar"]
