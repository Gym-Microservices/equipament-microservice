# Multi-stage Docker build for Equipment Service

# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy all POM files
COPY parent/pom.xml /app/parent/pom.xml
COPY core-microservice/pom.xml /app/core-microservice/pom.xml
COPY equipament-microservice/pom.xml /app/equipament-microservice/pom.xml

# Install parent POM
RUN cd /app/parent && mvn install -N

# Build core first
COPY core-microservice/src /app/core-microservice/src
RUN cd /app/core-microservice && mvn clean install -DskipTests

# Download microservice dependencies
RUN mkdir -p /app/equipament-microservice/src/main/java/temp && \
    echo "public class Temp {}" > /app/equipament-microservice/src/main/java/temp/Temp.java

RUN cd /app/equipament-microservice && mvn dependency:go-offline -DskipTests

# Clean temp files
RUN rm -rf /app/equipament-microservice/src/main/java/temp

# Build equipment service
COPY equipament-microservice/src /app/equipament-microservice/src
RUN cd /app/equipament-microservice && mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/equipament-microservice/target/equipament-microservice-*.jar app.jar

# Expose port
EXPOSE 8083

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
