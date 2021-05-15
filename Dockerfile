FROM adoptopenjdk/openjdk11:latest
COPY target/application.jar /home/app.jar
CMD ["java","-jar","/home/app.jar"]
