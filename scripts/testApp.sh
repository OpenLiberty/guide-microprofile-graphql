#!/bin/bash
set -euxo pipefail

./scripts/packageApps.sh

mvn -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -pl system liberty:create liberty:install-feature liberty:deploy
mvn -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -pl graphql liberty:create liberty:install-feature liberty:deploy

mvn -pl system liberty:start
mvn -pl graphql liberty:start

mvn -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -pl system failsafe:integration-test
mvn -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -pl graphql failsafe:integration-test

mvn -pl system failsafe:verify
mvn -pl graphql failsafe:verify

mvn -pl system liberty:stop
mvn -pl graphql liberty:stop
