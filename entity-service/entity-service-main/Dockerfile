FROM amazoncorretto:21-al2
HEALTHCHECK --interval=3s --retries=20 CMD curl -f http://localhost:8083/actuator/health || exit 1
COPY entity-main-app/build/libs/entity-main-app.jar service.jar
CMD java ${JAVA_OPTS:-"-Xmx64m"} -jar ${JAR:-"service.jar"}