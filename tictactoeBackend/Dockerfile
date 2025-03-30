# Use an official Maven image with Java 21 to build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy only the pom.xml file to leverage Docker's caching mechanism
COPY pom.xml .

# Download dependencies without building the project to optimize caching
RUN mvn dependency:go-offline

# Copy the entire project
COPY . .

# Build the application without running tests
RUN mvn clean package -DskipTests

# Use a minimal Java 21 runtime image to run the application
FROM eclipse-temurin:21-jdk-jammy

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file from the build image
COPY --from=build /app/target/tictactoe-0.0.1-SNAPSHOT.jar demo.jar

# Expose the application port
EXPOSE 8080

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "demo.jar"]