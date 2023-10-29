#!/bin/bash

# Clean and build the project using Maven
mvn clean install

# Run the JAR file
java -jar target/flowerManagement-0.0.1-SNAPSHOT.jar

