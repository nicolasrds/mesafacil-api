FROM maven:3-eclipse-temurin-17-alpine AS builder
WORKDIR app

COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine

RUN addgroup -S mesafacil && adduser -S mesafacil -G mesafacil
USER mesafacil
COPY --from=builder /app/target/*.jar /app/mesafacil-api.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar","-Duser.timezone=America/Fortaleza", "/app/mesafacil-api.jar"]