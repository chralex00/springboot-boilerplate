include .env

up_dev:
	docker-compose -f docker-compose-development.yml up -d

down_dev:
	docker-compose -f docker-compose-development.yml down
	docker rmi mysql:5.7
	docker rmi phpmyadmin/phpmyadmin

build:
	docker build -t ${DOCKER_SERVICE_IMAGE_NAME} .

up:
	docker-compose up -d

down:
	docker-compose down
	docker rmi ${DOCKER_SERVICE_IMAGE_NAME}

logs:
	docker logs -f ${DOCKER_SERVICE_CONTAINER_NAME} --tail=500