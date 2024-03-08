# we will use openjdk 8 with alpine as it is a very small linux distro
#FROM openjdk
FROM sapmachine:17-jre-headless-ubuntu
#FROM gcr.io/distroless/java17-debian11:nonroot
COPY ./demo-k8s/build/libs/demo-k8s-0.0.1-SNAPSHOT.jar /opt/demo-k8s.jar
EXPOSE 8080
CMD ["java", "-jar", "/opt/demo-k8s.jar"]
