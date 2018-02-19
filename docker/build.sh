#!/bin/bash

cd ..
mvn clean package
cp catalogue-standalone/target/catalogue-jar-with-dependencies.jar \
   docker/catalogue.jar
cd docker
docker build -t i3s-catalogue .
