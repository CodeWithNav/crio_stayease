# Use an official Maven image to build the project
FROM openjdk:21-jdk AS build

WORKDIR /app


# Copy the Maven wrapper scripts and pom.xml
COPY .mvn /app/.mvn
COPY mvnw pom.xml /app/

# Ensure Maven wrapper is executable
RUN chmod +x /app/mvnw
# Download Maven dependencies and build the application
RUN ./mvnw dependency:go-offline -B
COPY src /app/src
RUN ./mvnw package -DskipTests

# Stage 2: Runtime stage using OpenJDK 21
FROM openjdk:21-jdk-slim


WORKDIR /app

# Copy the jar file from the build stage to the final stage
COPY --from=build /app/target/stayease-0.0.1-SNAPSHOT.jar /app/app.jar


# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
