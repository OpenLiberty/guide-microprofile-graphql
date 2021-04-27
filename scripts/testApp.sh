#!/bin/bash
set -euxo pipefail

mvn -q clean package 
mvn -pl models install
mvn -pl backend liberty:create liberty:install-feature liberty:deploy
mvn -pl system liberty:create liberty:install-feature liberty:deploy
mvn -pl backend liberty:start
mvn -pl system liberty:start
mvn -pl backend failsafe:integration-test
mvn -pl system failsafe:integration-test
mvn -pl backend failsafe:verify
mvn -pl system failsafe:verify
mvn -pl backend liberty:stop
mvn -pl system liberty:stop

