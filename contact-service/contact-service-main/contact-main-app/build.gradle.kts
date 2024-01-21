plugins {
    application
    id("com.codedifferently.studycrm.java-web-application")
}

dependencies {
    implementation("com.codedifferently.studycrm.contact-service.web:contact-web-lib")
}

application {
    mainClass.set("com.codedifferently.studycrm.contacts.App")
}