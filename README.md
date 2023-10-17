# segment-middleware
A middleware for the Segment app.

### Prerequisites
This service requires the following tools:
- Java >= v1.8.0
- Apache Maven v3.9.4
- Docker >= v24.0.5
- Docker Compose >= v2.20.2

### Configuration
This middleware requires an `application.properties` configuration file, under the `src/main/resources/` folder.
The `application.properties` file must contain the following application properties:
- `server.port`: the service port, preferably a number between 8090 and 8099.
- `service.version`: the service name. It must be the same as <artifactId /> in the pom.xml file.
- `service.name`: the service version. It must be the same as <version /> in the pom.xml file.
- `security.api-key-http-header-name`: the HTTP header name of the API Key.
- `security.api-key-http-header-value`: the HTTP header value of the API Key.
- `security.password-hashing.secret`: the hash secret, used for the password encryption.
- `security.password-hashing.iterations`: the number of iterations of the hash algorithm, like 2048, or 4096.
- `security.password-hashing.hash-width`: the hash width, expressed in bytes, like 256, or 512.
- `security.password-hashing.salt-width`: the salt width, expressed in bytes, like 128, or 256.
- `security.password-hashing.memory-costs`: the memory costs of the hash algorithm, like 2048, or 4096.
- `security.jwt-generation.secret`: the secret, used for the JWT (Json Web Token) generation.
- `security.jwt-generation.duration`: the JWT duration, expressed in milliseconds, like 54000000 (15 minutes).
- `spring.jpa.hibernate.ddl-auto`: the data schema update stratagy. See https://docs.spring.io/spring-boot/docs/1.1.0.M1/reference/html/howto-database-initialization.html for more information. It is strongly recommended to use the value none when the software is in production.
- `spring.datasource.url`: the URL of the MySQL DBMS including host, port and database name.
- `spring.datasource.username`: the database user's userename.
- `spring.datasource.password`: the database user's password.
- `spring.datasource.driver-class-name`: the fully qualified package name of the mysql driver Java class.
- `spring.jpa.database-platform`: specify the database dialect, like MySQLDialect.

An `application.properties` template is stored at the same directory in the `application.properties.example` file, so run:
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```
to create a new `application.properties` file and set the configurations.

To run the Dockerized middleware, it is necessary to create a .env file with the following env variables:
- `DOCKER_SERVICE_CONTAINER_NAME`: the name of the docker container, like segment-middleware.
- `DOCKER_SERVICE_IMAGE_NAME`: the name of the docker image, like segment-middleware.
- `DOCKER_SERVICE_PORT`: the listening port of the docker container, like 8091, or 8092. It must be the same of the `server.port` property, present in the `application.properties` file.
- `DOCKER_MYSQL_ROOT_PASSWORD`: the root password of MySQL.
- `DOCKER_MYSQL_DATABASE`: the MySQL database name.
- `DOCKER_MYSQL_USER`: the  username to access the MySQL database.
- `DOCKER_MYSQL_PASSWORD`: the password to access the MySQL database.
- `DOCKER_MYSQL_EXPOSED_PORT`: the listening port of MySQL reachable outside the docker container.
- `DOCKER_PHPMYADMIN_PORT`: the listening port of PHPMyAdmin reachable outside the docker container.
- `DOCKER_MONGO_INITDB_ROOT_USERNAME`: the root username of MongoDB.
- `DOCKER_MONGO_INITDB_ROOT_PASSWORD`: the root password of MongoDB.
- `DOCKER_MONGO_INITDB_DATABASE`: the MongoDB database name.
- `DOCKER_MONGO_EXPOSED_PORT`: the listening port of MongoDB reachable outside the docker container.
- `DOCKER_MONGO_EXPRESS_ADMIN_AUTH_USERNAME`: the root username to access the MongDB database.
- `DOCKER_MONGO_EXPRESS_ADMIN_AUTH_PASSWORD`: the root password to access the MongDB database.
- `DOCKER_MONGO_EXPRESS_BASIC_AUTH_USERNAME`: the basic username to access the MongDB database.
- `DOCKER_MONGO_EXPRESS_BASIC_AUTH_PASSWORD`: the basic password to access the MongDB database.
- `DOCKER_MONGO_EXPRESS_EXPOSED_PORT`: the listening port of Mongo Express reachable outside the docker container.

To easily create the .env file, run the following command:
```bash
cp .env_example .env
```
to copy the .env_example file in the .env file, then configure all the env vars.

### Cleaning
```bash
mvn clean
```

### Installing dependencies (with Cleaning)
```bash
mvn clean install
```

### Running (with the Dockerized MySQL and PhpMyAdmin)
```bash
docker-compose -f ./docker-compose-development.yml up -d

mvn clean spring-boot:run
# or without cleaning
mvn spring-boot:run
```

### Running the Dockerized Middleware (with the Dockerized MySQL and PhpMyAdmin)
```bash
# docker-compose for mysql and phpmyadmin
docker-compose -f ./docker-compose-development.yml up -d

# cleaning and building the middleware
mvn clean install

# docker-compose for the middleware
docker-compose up -d
```
