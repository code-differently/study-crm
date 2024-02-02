plugins {
    id("com.codedifferently.studycrm.spring-library")
}

group = "com.codedifferently.studycrm.auth-service.persistence"

dependencies {
    implementation("com.codedifferently.studycrm.auth-service.domain:auth-domain-lib")

    implementation("io.eventuate.common:eventuate-common-jdbc")
}