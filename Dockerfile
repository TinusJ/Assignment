FROM eclipse-temurin:17-jdk-focal
LABEL authors="Tinus"

WORKDIR /app

COPY ./target/assignment-0.0.1-SNAPSHOT.jar ./app.jar

ENTRYPOINT ["java", "-jar","app.jar"]