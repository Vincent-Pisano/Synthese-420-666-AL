FROM maven:3.8.3-adoptopenjdk-15

CMD mvn clean package
ARG JAR_FILE=target/order-1.0.jar
COPY ${JAR_FILE} ./order.jar

ENTRYPOINT ["java","-jar","./order.jar"]