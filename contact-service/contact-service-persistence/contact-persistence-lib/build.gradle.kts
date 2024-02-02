plugins {
    id("com.codedifferently.studycrm.spring-library")
}

group = "com.codedifferently.studycrm.contact-service.persistence"

dependencies {
    implementation("com.codedifferently.studycrm.contact-service.domain:contact-domain-lib")

    implementation("io.eventuate.common:eventuate-common-jdbc")
}