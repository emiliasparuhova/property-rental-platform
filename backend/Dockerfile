FROM gradle:7.5.0-jdk17
WORKDIR /opt/app
COPY ./build/libs/individual-project-backend-0.0.1-SNAPSHOT.jar ./

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar individual-project-backend-0.0.1-SNAPSHOT.jar"]
#
#FROM openjdk:17-alpine
#ARG JAR_FILE=build/libs/*.jar
#COPY ${JAR_FILE} app.jar
#EXPOSE 8083
#ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/app.jar"]