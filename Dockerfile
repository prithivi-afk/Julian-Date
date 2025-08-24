# Use Maven to build the app
FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run the built JAR with a lightweight JDK image
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/julian-app-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
