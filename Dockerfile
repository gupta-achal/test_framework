FROM  bellsoft/liberica-openjdk-alpine:17.0.8

#install curl jq
RUN apk add curl jq

#workspace
WORKDIR /home/selenium-docker

#Add the required file
ADD target/docker-resources ./
ADD runner.sh runner.sh

#Run the test
# java -Dselenium.grid.enabled=true -cp 'libs/*' org.testng.TestNG com/w2a/runner/${TEST_SUITE}
ENTRYPOINT sh runner.sh
