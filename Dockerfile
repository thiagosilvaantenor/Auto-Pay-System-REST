FROM eclipse-temurin:17-jdk-alpine

LABEL maintainer="Thiago Silva Antenor <thiagoantenor31@gmail.com"

WORKDIR /app

COPY target/*jar auto-pay-system-rest-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "auto-pay-system-rest-0.0.1-SNAPSHOT.jar"]