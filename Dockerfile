FROM openjdk:11-jre-slim-stretch

COPY "/target/authorizationServer-0.0.1-SNAPSHOT.jar" "/opt"
COPY "./docker-entrypoint.sh" "/opt"

EXPOSE 8081

ENTRYPOINT [ "/opt/docker-entrypoint.sh" ]
