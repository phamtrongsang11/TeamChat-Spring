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

# Use a base image with JDK 17
FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src/ ./src/

RUN mvn package -DskipTests

FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /build/libs/demo-1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]