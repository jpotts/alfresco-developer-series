#!/bin/bash
# Note. This script requires Alfresco.war to be running in another Tomcat on port 8080

if [[ -z ${MAVEN_OPTS} ]]; then
    echo "The environment variable 'MAVEN_OPTS' is not set, setting it for you";

    # Downloads the spring-loaded lib if not existing and runs the Share AMP applied to Share WAR
    springloadedfile=~/.m2/repository/org/springframework/springloaded/1.2.5.RELEASE/springloaded-1.2.5.RELEASE.jar

    if [ ! -f $springloadedfile ]; then
        mvn validate -Psetup
    fi

    # Spring loaded can be used with the Share AMP project in 5.1
    # (i.e. it does not have the same problem as Repo AMP and AIO)
    MAVEN_OPTS="-javaagent:$springloadedfile -noverify"
fi
echo "MAVEN_OPTS is set to '$MAVEN_OPTS'";
mvn clean install -Pamp-to-war