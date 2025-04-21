# Use a base image with JDK installed
FROM openjdk:21-jdk-slim

# Set environment variables
ENV APP_HOME=/app
WORKDIR $APP_HOME

# Copy the built jar file into the container
COPY target/*.jar mortgage_eligibility_checker.jar

# Expose the port your Spring Boot app runs on (typically 8080)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "mortgage_eligibility_checker.jar"]
