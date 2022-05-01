FROM maven:3.8.3-adoptopenjdk-15

CMD mvn clean package
ARG JAR_FILE=target/auth-1.0.jar
COPY ${JAR_FILE} ./auth.jar

ENTRYPOINT ["java","-jar","./auth.jar"]