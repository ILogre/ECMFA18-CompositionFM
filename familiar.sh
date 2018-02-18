#!/bin/bash

IMAGE=petitroll/familiar:1.2

command -v docker > /dev/null
if [ "$?" -ne "0" ]; then
  echo "Error: requiring docker"
  exit 1
fi

docker run -v $PWD:/familiar/host -it $IMAGE $@
