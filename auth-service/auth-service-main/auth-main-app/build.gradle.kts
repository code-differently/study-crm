plugins {
    application
    id("com.codedifferently.studycrm.java-web-application")
}

group = "com.codedifferently.studycrm.auth-service.main"

dependencies {
    implementation("com.codedifferently.studycrm.auth-service.web:auth-web-lib")
    implementation("com.codedifferently.studycrm.auth-service.messaging:auth-messaging-lib")
    implementation("com.codedifferently.studycrm.auth-service.persistence:auth-persistence-lib")
}

application {
    mainClass.set("com.codedifferently.studycrm.auth.App")
}