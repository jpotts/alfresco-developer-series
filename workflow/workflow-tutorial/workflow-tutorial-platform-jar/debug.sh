#!/bin/bash

MAVEN_OPTS="-Xms256m -Xmx2G" mvnDebug clean install alfresco:run
