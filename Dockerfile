FROM openjdk:11
ADD target/car-service.jar car-service.jar
ENTRYPOINT ["java", "-jar","car-service.jar"]
EXPOSE 8080