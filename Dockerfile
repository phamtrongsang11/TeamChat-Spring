FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy your application jar file
COPY build/libs/*.jar app.jar

# Expose the port your application runs on (adjust as needed)
EXPOSE 8080

# Start the application using java
CMD ["java", "-jar", "app.jar"]