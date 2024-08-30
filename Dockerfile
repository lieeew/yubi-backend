FROM openjdk:17

WORKDIR /bi

COPY ./target/yubi-backend-0.0.1-SNAPSHOT.jar /bi/your-application.jar

EXPOSE 9090

# CMD ["java", "-jar", "your-application.jar"]
CMD ["echo", "Docker Image created success!"]