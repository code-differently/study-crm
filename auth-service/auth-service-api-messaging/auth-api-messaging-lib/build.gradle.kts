plugins {
    id("com.codedifferently.studycrm.java-library")
}

group = "com.codedifferently.studycrm.auth-service.api.messaging"

dependencies {
    implementation("com.codedifferently.studycrm.auth-service.domain:auth-domain-lib")

    implementation("io.eventuate.tram.core:eventuate-tram-spring-commands")
}