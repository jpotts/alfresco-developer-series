::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::      Dev environment startup script for Alfresco Community.    ::
::                                                                ::
::      Downloads the spring-loaded lib if not existing and       ::
::      runs the Repo AMP applied to Alfresco WAR.                ::
::      Note. the Share WAR is not deployed.                      ::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
@echo off

set springloadedfile=%HOME%\.m2\repository\org\springframework\springloaded\1.2.3.RELEASE\springloaded-1.2.3.RELEASE.jar

if not exist %springloadedfile% (
  mvn validate -Psetup
)

set MAVEN_OPTS=-javaagent:"%springloadedfile%" -noverify -Xms256m -Xmx2G

mvn integration-test -Pamp-to-war -nsu
:: mvn integration-test -Pamp-to-war 
