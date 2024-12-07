FROM maven:3.9.9-eclipse-temurin-23 AS build
COPY . /app
WORKDIR /app

RUN mvn clean install -DskipTests=true

CMD ["mvn", "test"]