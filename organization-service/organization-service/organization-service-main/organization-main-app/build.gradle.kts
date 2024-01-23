plugins {
    application
    id("com.codedifferently.studycrm.java-web-application")
}

dependencies {
    implementation("com.codedifferently.studycrm.organization-service.web:organization-web-lib")
}

application {
    mainClass.set("com.codedifferently.studycrm.organizations.App")
}