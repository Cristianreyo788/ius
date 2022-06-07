FROM openjdk:8-alpine
ADD target/ /usr/share/
ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/libranza-service.jar"]