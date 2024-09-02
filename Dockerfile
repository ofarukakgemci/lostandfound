# Use a base image with OpenJDK
FROM eclipse-temurin:21-alpine

# Set the working directory
WORKDIR /app

# Copy the application JAR
COPY build/libs/lostandfound-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
