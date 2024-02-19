FROM maven:3.8.7-openjdk-18 as build
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY ./ ./
RUN mvn clean install -D maven.test.skip=true && ls -l

FROM openjdk:19-alpine3.16 as prod
WORKDIR /app
COPY --from=build /app/target/springboot-boilerplate.jar /app
ENTRYPOINT ["java","-jar","./springboot-boilerplate.jar"]