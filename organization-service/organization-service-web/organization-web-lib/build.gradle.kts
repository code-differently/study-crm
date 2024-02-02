plugins {
    id("com.codedifferently.studycrm.spring-web-library")
}

group = "com.codedifferently.studycrm.organization-service.web"

dependencies {
    implementation("com.codedifferently.studycrm.common.domain:common-domain-lib")
    implementation("com.codedifferently.studycrm.common.web:common-web-lib")
    implementation("com.codedifferently.studycrm.organization-service.domain:organization-domain-lib")
    implementation("com.codedifferently.studycrm.organization-service.persistence:organization-persistence-lib")
    implementation("com.codedifferently.studycrm.organization-service.api.web:organization-api-web-lib")
    implementation("com.codedifferently.studycrm.organization-service.sagas:organization-sagas-lib")

	implementation("org.springframework.security:spring-security-config")
    implementation("org.springframework.security:spring-security-oauth2-jose")
    implementation("org.springframework.security:spring-security-oauth2-resource-server")
}