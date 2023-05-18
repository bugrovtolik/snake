FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8000
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} snake.jar
ENTRYPOINT ["java","-jar","/snake.jar"]
