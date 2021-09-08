FROM openjdk:16
EXPOSE 8080
ADD sample.db sample.db
ADD target/jumia-exercise.jar jumia-exercise.jar
ENTRYPOINT ["java","-jar","/jumia-exercise.jar"]