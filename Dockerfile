FROM openjdk:8
EXPOSE 8084
ADD target/reservation-app.jar reservation-app.jar
ENTRYPOINT ["java", "-jar", "/reservation-app.jar"]