plugins {
    id("com.codedifferently.studycrm.spring-library")
}

group = "com.codedifferently.studycrm.entity-service.persistence"

dependencies {
    implementation("com.codedifferently.studycrm.entity-service.domain:entity-domain-lib")

    implementation("io.eventuate.common:eventuate-common-jdbc")
}