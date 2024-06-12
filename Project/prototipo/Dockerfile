FROM openjdk:8-jdk-alpine
COPY /registry registry
COPY ./target/prototipo-1.0-SNAPSHOT.war prototipo-1.0.war
ENTRYPOINT [ "java", "-jar", "/prototipo-1.0.war" ]