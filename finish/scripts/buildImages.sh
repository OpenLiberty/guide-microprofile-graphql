#!/bin/bash

echo Building images

docker build -t system:1.0-java25-SNAPSHOT --build-arg JAVA_VERSION=java25 system/. &
docker build -t system:1.0-java17-SNAPSHOT --build-arg JAVA_VERSION=java17 system/. &
docker build -t graphql:1.0-SNAPSHOT graphql/. &

wait
echo Images building completed
