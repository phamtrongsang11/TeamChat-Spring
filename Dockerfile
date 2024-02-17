# # Use a multi-stage build for efficiency
# FROM ubuntu:latest AS build

# RUN apt-get update
# RUN apt-get install openjdk-17-jdk -y
# # Set working directory
# WORKDIR /app

# # Copy the pom.xml and source code
# COPY pom.xml ./
# COPY . .

# # Install dependencies
# RUN mvn clean install

# # Build the JAR file
# RUN mvn package

# # Switch to a slimmer runtime image
# FROM openjdk:17-jdk-slim

# # Copy the JAR file from the builder stage
# COPY --from=builder /app/target/*.jar app.jar

# # Set the working directory
# WORKDIR /app

# # Expose the port where your Spring application listens (usually 8080)
# EXPOSE 8080

# # Start the Spring application using the JAR file
# ENTRYPOINT ["java", "-jar", "app.jar"]

# Base image with Java 17
FROM eclipse-temurin:17-jre

# Set working directory
WORKDIR /app

# Copy Maven dependencies
COPY ./target/*.jar app.jar

# Expose port for the Spring application (adjust if needed)
EXPOSE 8080

# Command to run the Spring application
CMD ["java", "-jar", "app.jar"]