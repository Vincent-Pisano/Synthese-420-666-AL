FROM maven:3.8.3-adoptopenjdk-15
MAINTAINER vincentRenaud
CMD mvn clean package
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ./auth.jar
ENTRYPOINT ["java","-jar","./auth.jar"]