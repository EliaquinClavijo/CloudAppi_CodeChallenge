FROM openjdk:11

VOLUME [ "/tmp" ]

EXPOSE 8080

ADD ./target/CLOUDAPPI.ApiUsers-0.0.1-SNAPSHOT.jar users-service.jar

ENTRYPOINT [ "java", "-jar", "users-service.jar" ]
