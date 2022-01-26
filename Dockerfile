FROM openjdk:11
ARG JAR_FILE=build/libs/atmservice-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} atmservice.jar
ENTRYPOINT ["java","-jar","atmservice.jar"]
EXPOSE 8080
