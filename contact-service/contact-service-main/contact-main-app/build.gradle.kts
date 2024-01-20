plugins {
    application
    id("com.codedifferently.studycrm.java-web-application")
}

dependencies {
    implementation("com.codedifferently.studycrm.contact-service.web:contact-web-lib")
}

application {
    // Define the main class for the application.
    mainClass.set("com.codedifferently.studycrm.contacts.App")
}