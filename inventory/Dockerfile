FROM maven:3.8.3-adoptopenjdk-15

CMD mvn clean package
ARG JAR_FILE=target/inventory-1.0.jar
COPY ${JAR_FILE} ./inventory.jar

ENTRYPOINT ["java","-jar","./inventory.jar"]