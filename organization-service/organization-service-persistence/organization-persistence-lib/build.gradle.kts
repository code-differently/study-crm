plugins {
    id("com.codedifferently.studycrm.spring-library")
}

group = "com.codedifferently.studycrm.organization-service.persistence"

dependencies {
    implementation("com.codedifferently.studycrm.organization-service.domain:organization-domain-lib")

    implementation("io.eventuate.common:eventuate-common-jdbc")
}