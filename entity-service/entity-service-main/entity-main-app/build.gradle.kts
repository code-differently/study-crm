plugins {
    application
    id("com.codedifferently.studycrm.java-web-application")
}

group = "com.codedifferently.studycrm.entity-service.main"

dependencies {
    implementation("com.codedifferently.studycrm.common.web:common-web-lib")
    implementation("com.codedifferently.studycrm.entity-service.web:entity-web-lib")
}

application {
    if (project.hasProperty("prod")) {
        applicationDefaultJvmArgs = listOf("-Dspring.profiles.active=production")
    } else {
        applicationDefaultJvmArgs = listOf("-Dspring.profiles.active=development")
    }
    mainClass.set("com.codedifferently.studycrm.entities.App")
}