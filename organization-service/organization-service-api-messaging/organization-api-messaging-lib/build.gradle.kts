plugins {
    id("com.codedifferently.studycrm.spring-library")
}

group = "com.codedifferently.studycrm.organization-service.api.messaging"

dependencies {
    implementation("com.codedifferently.studycrm.organization-service.domain:organization-domain-lib")
    implementation("com.codedifferently.studycrm.organization-service.api.web:organization-api-web-lib")

    implementation("io.eventuate.tram.core:eventuate-tram-spring-commands")
}