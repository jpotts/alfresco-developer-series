#!/bin/sh

export COMPOSE_FILE_PATH=${PWD}/target/classes/docker/docker-compose.yml

if [ -z "${M2_HOME}" ]; then
  export MVN_EXEC="mvn"
else
  export MVN_EXEC="${M2_HOME}/bin/mvn"
fi

start() {
    docker volume create webscripts-tutorial-acs-volume
    docker volume create webscripts-tutorial-db-volume
    docker volume create webscripts-tutorial-ass-volume
    docker-compose -f $COMPOSE_FILE_PATH up --build -d
}

start_share() {
    docker-compose -f $COMPOSE_FILE_PATH up --build -d webscripts-tutorial-share
}

start_acs() {
    docker-compose -f $COMPOSE_FILE_PATH up --build -d webscripts-tutorial-acs
}

down() {
    docker-compose -f $COMPOSE_FILE_PATH down
}

purge() {
    docker volume rm webscripts-tutorial-acs-volume
    docker volume rm webscripts-tutorial-db-volume
    docker volume rm webscripts-tutorial-ass-volume
}

build() {
    docker rmi alfresco-content-services-webscripts-tutorial:development
    docker rmi alfresco-share-webscripts-tutorial:development
    $MVN_EXEC clean install -DskipTests=true
}

build_share() {
    docker-compose -f $COMPOSE_FILE_PATH kill webscripts-tutorial-share
    yes | docker-compose -f $COMPOSE_FILE_PATH rm -f webscripts-tutorial-share
    docker rmi alfresco-share-webscripts-tutorial:development
    $MVN_EXEC clean install -DskipTests=true -pl webscripts-tutorial-share-jar
}

build_acs() {
    docker-compose -f $COMPOSE_FILE_PATH kill webscripts-tutorial-acs
    yes | docker-compose -f $COMPOSE_FILE_PATH rm -f webscripts-tutorial-acs
    docker rmi alfresco-content-services-webscripts-tutorial:development
    $MVN_EXEC clean install -DskipTests=true -pl webscripts-tutorial-platform-jar
}

tail() {
    docker-compose -f $COMPOSE_FILE_PATH logs -f
}

tail_all() {
    docker-compose -f $COMPOSE_FILE_PATH logs --tail="all"
}

test() {
    $MVN_EXEC verify -pl integration-tests
}

case "$1" in
  build_start)
    down
    build
    start
    tail
    ;;
  start)
    start
    tail
    ;;
  stop)
    down
    ;;
  purge)
    down
    purge
    ;;
  tail)
    tail
    ;;
  reload_share)
    build_share
    start_share
    tail
    ;;
  reload_acs)
    build_acs
    start_acs
    tail
    ;;
  build_test)
    down
    build
    start
    test
    tail_all
    down
    ;;
  test)
    test
    ;;
  *)
    echo "Usage: $0 {build_start|start|stop|purge|tail|reload_share|reload_acs|build_test|test}"
esac