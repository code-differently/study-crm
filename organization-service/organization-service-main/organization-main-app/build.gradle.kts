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
    mainClass.set("com.codedifferently.studycrm.organizations.App")
}