FROM openjdk:17-jdk-slim
COPY app/flowerManagement-0.0.1-SNAPSHOT.jar flowerManagement.jar
EXPOSE 8091
ENTRYPOINT ["java","-jar","/flowerManagement.jar"]