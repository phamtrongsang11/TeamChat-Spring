# Use a base image with Java JDK 17
FROM adoptopenjdk:17-jdk-hotspot

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and build the dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Expose the port on which the application will run
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/app.jar"]