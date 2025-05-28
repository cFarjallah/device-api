FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/deviceapi-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]