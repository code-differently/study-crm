plugins {
    application
    id("com.codedifferently.studycrm.java-web-application")
}

dependencies {
    implementation("com.codedifferently.studycrm.auth-service.web:auth-web-lib")
}

application {
    mainClass.set("com.codedifferently.studycrm.auth.App")
}