@ECHO Building images
@ECHO OFF

start /b docker build -q -t system:1.0-java25-SNAPSHOT --build-arg JAVA_VERSION=java25 system\.
start /b docker build -q -t system:1.0-java17-SNAPSHOT --build-arg JAVA_VERSION=java17 system\.
start /b docker build -q -t query:1.0-SNAPSHOT query\.
