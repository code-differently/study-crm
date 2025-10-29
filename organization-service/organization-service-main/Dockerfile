FROM amazoncorretto:21-al2
HEALTHCHECK --interval=3s --retries=20 CMD curl -f http://localhost:8082/actuator/health || exit 1
COPY organization-main-app/build/libs/organization-main-app.jar service.jar
CMD java ${JAVA_OPTS:-"-Xmx64m"} -jar ${JAR:-"service.jar"}