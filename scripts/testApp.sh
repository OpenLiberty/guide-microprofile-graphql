#!/bin/bash
set -euxo pipefail

./scripts/packageApps.sh

mvn -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -ntp -pl system liberty:create liberty:install-feature liberty:deploy
mvn -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -ntp -pl graphql liberty:create liberty:install-feature liberty:deploy

mvn -ntp -pl system liberty:start
mvn -ntp -pl graphql liberty:start

mvn -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -ntp -pl system failsafe:integration-test
mvn -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -ntp -pl graphql failsafe:integration-test

mvn -ntp -pl system failsafe:verify
mvn -ntp -pl graphql failsafe:verify

mvn -ntp -pl system liberty:stop
mvn -ntp -pl graphql liberty:stop
