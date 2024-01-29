plugins {
    application
    id("com.codedifferently.studycrm.java-web-application")
}

dependencies {
    implementation("com.codedifferently.studycrm.auth-service.web:auth-web-lib")
    implementation("com.codedifferently.studycrm.auth-service.messaging:auth-messaging-lib")
    implementation("com.codedifferently.studycrm.auth-service.persistence:auth-persistence-lib")

    implementation("io.eventuate.tram.sagas:eventuate-tram-sagas-spring-participant-starter")
}

application {
    mainClass.set("com.codedifferently.studycrm.auth.App")
}