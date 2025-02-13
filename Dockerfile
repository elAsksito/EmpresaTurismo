# Etapa de construcción (build)
FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM openjdk:17-jdk-slim

# Copia el script wait-for-it.sh
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# Copia el JAR construido en la etapa anterior
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto de la aplicación
EXPOSE 9999

# Espera a que la base de datos esté lista y luego ejecuta la aplicación
ENTRYPOINT ["/wait-for-it.sh", "db:3306", "--", "java", "-jar", "app.jar"]