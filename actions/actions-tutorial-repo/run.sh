#!/bin/bash
# Downloads the spring-loaded lib if not existing and      
# runs the Repo AMP applied to Alfresco WAR.           
# Note. the Share WAR is not deployed.              
springloadedfile=~/.m2/repository/org/springframework/springloaded/1.2.3.RELEASE/springloaded-1.2.3.RELEASE.jar

if [ ! -f $springloadedfile ]; then
mvn validate -Psetup
fi

MAVEN_OPTS="-javaagent:$springloadedfile -noverify -Xms256m -Xmx2G" mvn integration-test -Pamp-to-war
