plugins {
    id("com.codedifferently.studycrm.spring-library")
}

group = "com.codedifferently.studycrm.auth-service.api.messaging"

dependencies {
    implementation("io.eventuate.tram.core:eventuate-tram-spring-commands")
}