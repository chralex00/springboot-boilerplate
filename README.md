# springboot-boilerplate
A boilerplate for a Spring Boot project.

This boilerplate contains the minimum needed to start a Spring Boot project. The idea is to provide only and exclusively the basic scaffolding and utilities to develop a service with Sprint Boot.

This boilerplate has the fllowing utilities:
- [Spring Boot](https://spring.io/projects/spring-boot/)
- [Java](https://www.java.com/)
- API Docs, made with [Postman](https://www.postman.com/)
- [GNU Make](https://www.gnu.org/software/make/)
- [Docker](https://www.docker.com/)

### Prerequisites
This boilerplate requires the following tools:
- Java >= v1.8.0
- Apache Maven v3.9.4

The following dependencies are optional, but are still very convenient:
- Docker ^24.0.6
- Docker Compose ^2.22.0
- GNU Make ^3.81

### Configuration
This boilerplate requires an `application.properties` configuration file, under the `src/main/resources/` folder.
The `application.properties` file must contain the following application properties:
- `server.port`: the service port, preferably a number between 8090 and 8099.
- `service.version`: the service name. It must be the same as <artifactId /> in the pom.xml file.
- `service.name`: the service version. It must be the same as <version /> in the pom.xml file.

An `application.properties` template is stored at the same directory in the `application.properties.example` file, so run:
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```
to create a new `application.properties` file and set the configurations.

To run this boilerplate under Docker, it is necessary to create a `.env` file with the following env variables:
- `DOCKER_SERVICE_CONTAINER_NAME`: the name of the docker container, like `springboot-boilerplate`.
- `DOCKER_SERVICE_IMAGE_NAME`: the name of the docker image, like `springboot-boilerplate`.
- `DOCKER_SERVICE_PORT`: the listening port of the docker container, like 8091, or 8092. It must be the same of the `server.port` property, present in the `application.properties` file.

To easily create the .env file, run the following command:
```bash
cp .env_example .env
```
to copy the `.env_example` file in the .env file, then configure all the env vars.

### Cleaning
```bash
mvn clean
```

### Installing
```bash
mvn install
# or
mvn clean install
```

### Running (with the Dockerized MySQL and PhpMyAdmin)
```bash
mvn spring-boot:run
# or
mvn clean spring-boot:run
# or
make run
```

### Dockerizing
Building:
```bash
docker build -t springboot-boilerplate .  # or the choosen docker image name
# or
make docker-build
```

```bash
docker-compose up -d
# or
make docker-up
```

Logging:
```bash
docker logs -f springboot-boilerplate --tail=50 # or the choosen docker container name
# or
make docker-logs
```
