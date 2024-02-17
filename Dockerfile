# Use the official Maven image as the build environment
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and download the dependencies (to leverage Docker layer caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code to the container
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Use the official OpenJDK image as the runtime environment
FROM adoptopenjdk:17-jre-hotspot AS runtime

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage to the runtime environment
COPY --from=build /app/target/application.jar .

# Expose the port your application listens on (change it to your application's port)
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "your-application.jar"]