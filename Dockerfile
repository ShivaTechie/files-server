FROM maven:3.5-jdk-8-alpine
COPY ./target/file-server-0.0.1-SNAPSHOT.jar /app/jar/
WORKDIR /app/jar/
EXPOSE 8081:8081
ENTRYPOINT ["java","-jar","file-server-0.0.1-SNAPSHOT.jar"]