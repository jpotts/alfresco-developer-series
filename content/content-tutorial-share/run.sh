#!/bin/bash
# Downloads the spring-loaded lib if not existing and runs the Share AMP
springloadedfile=~/.m2/repository/org/springframework/springloaded/1.2.0.RELEASE/springloaded-1.2.0.RELEASE.jar

if [ ! -f $springloadedfile ]; then
mvn validate -Psetup
fi

MAVEN_OPTS="-javaagent:$springloadedfile -noverify" mvn integration-test -Pamp-to-war