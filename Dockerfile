# we will use openjdk 8 with alpine as it is a very small linux distro
#FROM openjdk
#FROM sapmachine:17-jre-headless-ubuntu
FROM gcr.io/distroless/java17-debian11:nonroot
COPY ./demo-app/build/libs/demo-app-0.0.1-SNAPSHOT.jar /opt/demo-app.jar
CMD ["java", "-jar", "/opt/demo-app.jar"]
