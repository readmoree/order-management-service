# First stage: Build the application
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy only the pom.xml first (to optimize Docker caching)
COPY pom.xml .

# Download dependencies (this speeds up subsequent builds)
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src src
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn

# Build the application (skipping tests for faster deployment)
RUN ./mvnw clean package -DskipTests

# Second stage: Run the application
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built JAR file from the first stage
COPY --from=build /app/target/Order-management-service.jar Order-management-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "Order-management-service.jar"]
