::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::      Dev environment startup script for Alfresco Community.    ::
::                                                                ::
::      Note. requires Alfresco.war to be running in another      ::
::      Tomcat on port 8080.                                      ::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
@echo off

mvn clean install alfresco:run
