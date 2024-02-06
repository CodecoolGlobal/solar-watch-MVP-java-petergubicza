# Dockerfile
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /tmp
COPY target/*.jar solar-watch-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","solar-watch-0.0.1-SNAPSHOT.jar"]