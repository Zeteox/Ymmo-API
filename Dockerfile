FROM maven:3.9.11-eclipse-temurin-21-alpine AS build

WORKDIR /build

COPY pom.xml ./
COPY src ./src

RUN mvn -q -DskipTests package

FROM eclipse-temurin:21-jre-alpine

WORKDIR /tmp

RUN apk add --no-cache curl

COPY --from=build /build/target/*.jar /app/app.jar

ENV SERVER_PORT=5000

EXPOSE 5000

CMD ["java", "-jar", "/app/app.jar"]
