FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

RUN mkdir -p /app/exports

COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 9999

ENTRYPOINT ["/wait-for-it.sh", "db:3306", "--", "java", "-jar", "app.jar"]