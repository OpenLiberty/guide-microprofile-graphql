@ECHO OFF
set NETWORK=graphql-app

docker network create %NETWORK%

docker run -d ^
  --network=$NETWORK ^
  --name=system-java8 ^
  --rm ^
  system:1.0-java8-SNAPSHOT &

start /b docker run -d ^
  --network=$NETWORK ^
  --name=system-java11 ^
  --rm ^
  system:1.0-java11-SNAPSHOT &

start /b docker run -d ^
  -p 9082:9082 ^
  --network=%NETWORK% ^
  --name=graphql ^
  --rm ^
  graphql:1.0-SNAPSHOT &
  
start /b docker run -d ^
  -e io.openliberty.guides.inventory.client.SystemClient/mp-graphql/url=http://graphql:9082/graphql ^
  -p 9081:9081 ^
  --network=%NETWORK% ^
  --name=inventory ^
  --rm ^
  inventory:1.0-SNAPSHOT 


