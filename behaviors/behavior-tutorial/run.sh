#!/bin/sh

export COMPOSE_FILE_PATH=${PWD}/target/classes/docker/docker-compose.yml

if [ -z "${M2_HOME}" ]; then
  export MVN_EXEC="mvn"
else
  export MVN_EXEC="${M2_HOME}/bin/mvn"
fi

start() {
    docker volume create behavior-tutorial-acs-volume
    docker volume create behavior-tutorial-db-volume
    docker volume create behavior-tutorial-ass-volume
    docker-compose -f $COMPOSE_FILE_PATH up --build -d
}

start_share() {
    docker-compose -f $COMPOSE_FILE_PATH up --build -d behavior-tutorial-share
}

start_acs() {
    docker-compose -f $COMPOSE_FILE_PATH up --build -d behavior-tutorial-acs
}

down() {
    docker-compose -f $COMPOSE_FILE_PATH down
}

purge() {
    docker volume rm behavior-tutorial-acs-volume
    docker volume rm behavior-tutorial-db-volume
    docker volume rm behavior-tutorial-ass-volume
}

build() {
    docker rmi alfresco-content-services-behavior-tutorial:development
    docker rmi alfresco-share-behavior-tutorial:development
    $MVN_EXEC clean install -DskipTests=true
}

build_share() {
    docker-compose -f $COMPOSE_FILE_PATH kill behavior-tutorial-share
    yes | docker-compose -f $COMPOSE_FILE_PATH rm -f behavior-tutorial-share
    docker rmi alfresco-share-behavior-tutorial:development
    $MVN_EXEC clean install -DskipTests=true -pl behavior-tutorial-share
}

build_acs() {
    docker-compose -f $COMPOSE_FILE_PATH kill behavior-tutorial-acs
    yes | docker-compose -f $COMPOSE_FILE_PATH rm -f behavior-tutorial-acs
    docker rmi alfresco-content-services-behavior-tutorial:development
    $MVN_EXEC clean install -DskipTests=true -pl behavior-tutorial-platform
}

tail() {
    docker-compose -f $COMPOSE_FILE_PATH logs -f
}

tail_all() {
    docker-compose -f $COMPOSE_FILE_PATH logs --tail="all"
}

test() {
    $MVN_EXEC verify -pl behavior-tutorial-integration-tests
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
