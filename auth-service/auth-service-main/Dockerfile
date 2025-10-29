FROM amazoncorretto:21
HEALTHCHECK --interval=3s --retries=20 CMD curl -f http://localhost:8081/actuator/health || exit 1
COPY auth-main-app/build/libs/auth-main-app.jar service.jar
CMD java ${JAVA_OPTS:-"-Xmx64m"} -jar ${JAR:-"service.jar"}