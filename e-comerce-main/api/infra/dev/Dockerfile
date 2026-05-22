FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package

FROM jetty:9.4-jre17-alpine
USER root
RUN apk add --no-cache su-exec
RUN printf '#!/bin/sh\nmkdir -p /var/lib/jetty/public\nchown jetty:jetty /var/lib/jetty/public\nexec su-exec jetty /docker-entrypoint.sh "$@"\n' > /entrypoint.sh && \
chmod +x /entrypoint.sh

COPY --from=build /app/target/*.war /var/lib/jetty/webapps/root.war

ENTRYPOINT ["/entrypoint.sh"]
EXPOSE 8080
