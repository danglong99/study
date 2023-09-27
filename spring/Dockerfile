FROM openjdk:20
WORKDIR /usr/src
ARG JAR_FILE=target/spring-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]