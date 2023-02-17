FROM eclipse-temurin:17-jre-alpine
ADD build/libs/*.jar /opt/root.jar
ENTRYPOINT ["java","-jar","/opt/root.jar"]