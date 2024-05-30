FROM openjdk:21
EXPOSE 8080
COPY target/student.jar student.jar
ENTRYPOINT ["java", "-jar", "/student.jar"]
