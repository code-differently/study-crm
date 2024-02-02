plugins {
    id("com.codedifferently.studycrm.spring-web-library")
}

group = "com.codedifferently.studycrm.contact-service.web"

dependencies {
    implementation("com.codedifferently.studycrm.common.domain:common-domain-lib")
    implementation("com.codedifferently.studycrm.contact-service.domain:contact-domain-lib")
    implementation("com.codedifferently.studycrm.contact-service.persistence:contact-persistence-lib")
    implementation("com.codedifferently.studycrm.contact-service.api.web:contact-api-web-lib")
}