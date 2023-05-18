# Start with a base image containing Java runtime
FROM gradle:7.6.1-jdk17-alpine AS build

# Make the project's directory the current directory
WORKDIR /home/gradle

# Copy the Gradle configuration files into the Docker image
COPY --chown=gradle:gradle gradle/ gradle/
COPY --chown=gradle:gradle build.gradle settings.gradle gradlew ./

# Copy the source code into the Docker image
COPY --chown=gradle:gradle src/ src/

# Build the project inside the Docker image
RUN ./gradlew clean bootJar

# Now build the final image
FROM eclipse-temurin:17-jdk-alpine

EXPOSE 8000

# Copy the built jar file from the build image to the final image
COPY --from=build /home/gradle/build/libs/*.jar snake.jar

# Set the startup command to execute the jar
ENTRYPOINT ["java", "-jar", "/snake.jar"]
