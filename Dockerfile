FROM openjdk:17-jdk-slim
COPY app/onlyh2/flowerManagement-0.0.1-SNAPSHOT.jar flowerManagement.jar
EXPOSE 8092
ENTRYPOINT ["java","-jar","/flowerManagement.jar"]