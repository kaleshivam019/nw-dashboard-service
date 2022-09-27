FROM eclipse-temurin:11
VOLUME /main-app
ADD target/nwdashboard-0.0.1-SNAPSHOT.jar nwdashboard-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "nwdashboard-0.0.1-SNAPSHOT.jar"]