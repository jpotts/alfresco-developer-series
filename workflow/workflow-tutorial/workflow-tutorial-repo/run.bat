::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::      Dev environment startup script for Alfresco Community     ::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
@echo off

set MAVEN_OPTS=-Xms256m -Xmx2G

mvn clean install alfresco:run
