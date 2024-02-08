plugins {
    application
    id("com.codedifferently.studycrm.java-web-application")
}

group = "com.codedifferently.studycrm.entity-service.main"

dependencies {
    implementation("com.codedifferently.studycrm.entity-service.web:entity-web-lib")
}

application {
    mainClass.set("com.codedifferently.studycrm.entities.App")
}