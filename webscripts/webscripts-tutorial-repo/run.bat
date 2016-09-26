@ECHO OFF

IF "%MAVEN_OPTS%" == "" (
    ECHO The environment variable 'MAVEN_OPTS' is not set, setting it for you
    SET MAVEN_OPTS=-Xms256m -Xmx2G -XX:PermSize=300m
)
ECHO MAVEN_OPTS is set to '%MAVEN_OPTS%'
mvn clean install -Pamp-to-war
