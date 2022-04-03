#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:12-jdk-alpine
ARG JAR_FILE=/home/app/target/*.jar
COPY --from=build ${JAR_FILE} api-gateway.jar
EXPOSE 8443
ENTRYPOINT ["java","-jar","/api-gateway.jar"]