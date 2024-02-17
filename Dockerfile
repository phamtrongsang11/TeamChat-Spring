# Use a multi-stage build for efficiency
FROM maven:3.8-jdk-17 AS builder

# Set working directory
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml ./
COPY . .

# Install dependencies
RUN mvn clean install

# Build the JAR file
RUN mvn package

# Switch to a slimmer runtime image
FROM openjdk:17-slim

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Set the working directory
WORKDIR /app

# Expose the port where your Spring application listens (usually 8080)
EXPOSE 8080

# Start the Spring application using the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]