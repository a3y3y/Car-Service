FROM openjdk:11
ADD target/web-service.jar web-service.jar
ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=docker", "web-service.jar"]
EXPOSE 8080