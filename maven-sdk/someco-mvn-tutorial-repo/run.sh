#!/bin/bash
if [[ -z ${MAVEN_OPTS} ]]; then
    echo "The environment variable 'MAVEN_OPTS' is not set, setting it for you";
    MAVEN_OPTS="-Xms256m -Xmx1524m -XX:PermSize=300m"
fi
echo "MAVEN_OPTS is set to '$MAVEN_OPTS'";
mvn clean install -Pamp-to-war