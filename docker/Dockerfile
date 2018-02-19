FROM openjdk:jre-alpine
MAINTAINER Sebastien Mosser

RUN mkdir catalogue catalogue/host

WORKDIR catalogue
COPY ./catalogue.jar .
VOLUME ./host

WORKDIR ./host
ENTRYPOINT ["java", "-jar", "../catalogue.jar"]
CMD []
