# Use the official maven/Java image to create a build artifact.
FROM maven:3.6-jdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Use the official OpenJDK image as the base image.
FROM openjdk:17-jre-slim
WORKDIR /app
COPY --from=build /app/target/*.jar line-0.0.1-SNAPSHOT.jar

# Set the application's jar file as the entrypoint of the container.
ENTRYPOINT ["java","-jar","/app/line-0.0.1-SNAPSHOT.jar"]
