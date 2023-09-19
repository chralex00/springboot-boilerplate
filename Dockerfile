FROM openjdk:19-jdk-alpine

ARG JAR_FILE=target/segment-middleware.jar

ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
