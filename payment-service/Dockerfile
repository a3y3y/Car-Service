FROM openjdk:11
ADD target/payment-service.jar payment-service.jar
ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=docker", "payment-service.jar"]
