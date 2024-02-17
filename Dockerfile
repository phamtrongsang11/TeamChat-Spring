# Use a multi-stage build for efficiency
FROM maven:3.22-jdk-17 AS builder

# Set working directory
WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml ./
COPY src/main/java ./src/main/java
COPY src/main/resources ./src/main/resources

# Install dependencies
RUN mvn package

# Switch to slim base image for runtime
FROM openjdk:17-jdk-slim

# Copy JAR file from builder stage
COPY --from=builder /app/target/*.jar app.jar


# Set working directory
WORKDIR /app

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]