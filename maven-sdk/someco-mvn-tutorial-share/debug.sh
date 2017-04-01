#!/bin/bash
# Note. requires Alfresco.war to be running in another Tomcat on port 8080

mvnDebug clean install alfresco:run
