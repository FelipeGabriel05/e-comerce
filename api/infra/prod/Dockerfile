FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM jetty:9.4-jre17-alpine
COPY --from=build /app/target/*.war /var/lib/jetty/webapps/root.war
EXPOSE 8080
