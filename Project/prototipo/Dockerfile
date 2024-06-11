FROM openjdk:8-jdk-alpine
COPY /registry registry
COPY ./target/prototipo-0.4-SNAPSHOT.war prototipo-0.4.war
ENTRYPOINT [ "java", "-jar", "/prototipo-0.4.war" ]