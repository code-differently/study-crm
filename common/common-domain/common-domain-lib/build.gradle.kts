plugins {
    id("com.codedifferently.studycrm.java-library")
}

group = "com.codedifferently.studycrm.common.domain"

dependencies {
    implementation("jakarta.persistence:jakarta.persistence-api");
    implementation("jakarta.transaction:jakarta.transaction-api");
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}