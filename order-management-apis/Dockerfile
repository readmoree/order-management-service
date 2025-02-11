# First stage: Build the application
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy only the pom.xml first (to optimize Docker caching)
COPY pom.xml .

# Download dependencies (this speeds up subsequent builds)
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY . .

# Build the application (skipping tests for faster deployment)
RUN mvn clean package -DskipTests

# Second stage: Run the application
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR file from the first stage
COPY --from=build /app/target/Order-management-service.jar Order-management-service.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "Order-management-service.jar"]
