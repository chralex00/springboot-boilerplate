include .env

clean:
	mvn clean

run:
	mvn clean spring-boot:run

docker-build:
	docker build -t ${DOCKER_SERVICE_IMAGE_NAME} .

docker-up:
	docker-compose up -d

docker-down:
	docker-compose down
	docker rmi ${DOCKER_SERVICE_IMAGE_NAME}

docker-logs:
	docker logs -f ${DOCKER_SERVICE_CONTAINER_NAME} --tail=50