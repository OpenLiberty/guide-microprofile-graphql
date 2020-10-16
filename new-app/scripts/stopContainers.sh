#!/bin/bash

docker stop system1 system2 system3 inventory kafka zookeeper

docker network rm reactive-app