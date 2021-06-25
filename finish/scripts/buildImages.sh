#!/bin/bash

echo Building images

docker build -t system:1.0-java8-SNAPSHOT --build-arg JAVA_VERSION=java8 system/. &
docker build -t system:1.0-java11-SNAPSHOT --build-arg JAVA_VERSION=java11 system/. &
docker build -t inventory:1.0-SNAPSHOT inventory/. &
docker build -t graphql:1.0-SNAPSHOT graphql/. &

wait
echo Images building completed
