FROM openjdk:23

EXPOSE 8081

ADD target/HW_Test-containers-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]