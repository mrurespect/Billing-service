FROM openjdk:17
VOLUME /tmp

COPY target/*.jar service.jar

ENTRYPOINT ["java","-jar","service.jar"]