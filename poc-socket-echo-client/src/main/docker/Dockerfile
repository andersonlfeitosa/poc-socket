FROM openjdk:8-jre-alpine
RUN mkdir -p /opt/microservices
RUN adduser -D -u 1000 usrjava
RUN chown -R usrjava /opt/
WORKDIR /opt/microservices
EXPOSE 8080
EXPOSE 9090
USER usrjava
ADD maven/poc-socket-echo-client.jar /opt/microservices/poc-socket-echo-client.jar
ENTRYPOINT ["java", "-Duser.timezone=America/Sao_Paulo", "-Duser.country=br", "-Duser.language=pt" ,"-Djava.security.egd=file:/dev/./urandom", "-Xmx1024m", "-jar", "poc-socket-echo-client.jar"]
