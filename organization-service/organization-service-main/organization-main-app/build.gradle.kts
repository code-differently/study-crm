plugins {
    application
    id("com.codedifferently.studycrm.java-web-application")
}

group = "com.codedifferently.studycrm.organization-service.main"

dependencies {
    implementation("com.codedifferently.studycrm.common.web:common-web-lib")
    implementation("com.codedifferently.studycrm.organization-service.web:organization-web-lib")
}

application {
    if (project.hasProperty("prod")) {
        applicationDefaultJvmArgs = listOf("-Dspring.profiles.active=production")
    } else {
        applicationDefaultJvmArgs = listOf("-Dspring.profiles.active=development")
    }
    mainClass.set("com.codedifferently.studycrm.organizations.App")
}