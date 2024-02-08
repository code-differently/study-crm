plugins {
    id("com.codedifferently.studycrm.spring-web-library")
}

group = "com.codedifferently.studycrm.common.web"

dependencies {
    implementation("org.springframework.security:spring-security-oauth2-resource-server")
}