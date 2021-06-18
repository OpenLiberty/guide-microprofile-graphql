#!/bin/bash
set -euxo pipefail

mvn -q clean package 
mvn -pl models install
mvn -pl system liberty:create liberty:install-feature liberty:deploy
mvn -pl graphql liberty:create liberty:install-feature liberty:deploy
mvn -pl inventory liberty:create liberty:install-feature liberty:deploy
mvn -pl system liberty:start
mvn -pl graphql liberty:start
mvn -pl inventory liberty:start
mvn -pl system failsafe:integration-test
mvn -pl graphql failsafe:integration-test
mvn -pl inventory failsafe:integration-test
mvn -pl system failsafe:verify
mvn -pl graphql failsafe:verify
mvn -pl inventory failsafe:verify
mvn -pl inventory liberty:stop
mvn -pl graphql liberty:stop
mvn -pl system liberty:stop

