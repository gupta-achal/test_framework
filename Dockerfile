FROM  bellsoft/liberica-openjdk-alpine:17.0.8
WORKDIR /home/selenium-docker
ADD target/docker-resources ./

