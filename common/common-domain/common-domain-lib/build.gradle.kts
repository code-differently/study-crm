plugins {
    id("com.codedifferently.studycrm.java-library")
}

group = "com.codedifferently.studycrm.common.domain"

dependencies {
    implementation("jakarta.persistence:jakarta.persistence-api");
    implementation("jakarta.transaction:jakarta.transaction-api");
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.security:spring-security-acl")
}