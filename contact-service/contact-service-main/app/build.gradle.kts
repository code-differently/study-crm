plugins {
    application
    id("com.simplecrm.spring-boot-application")
}

dependencies {
    implementation("com.simplecrm.contact-service.web:lib")
}

application {
    // Define the main class for the application.
    mainClass.set("com.simplecrm.contacts.App")
}