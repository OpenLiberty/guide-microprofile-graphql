#!/bin/bash
set -euxo pipefail

./scripts/packageApps.sh

mvn -pl system liberty:start
mvn -pl graphql liberty:start

mvn -pl system failsafe:integration-test
mvn -pl graphql failsafe:integration-test
mvn -pl system failsafe:verify
mvn -pl graphql failsafe:verify

mvn -pl system liberty:stop
mvn -pl graphql liberty:stop
