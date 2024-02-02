plugins {
    id("java-library")
    id("com.codedifferently.studycrm.java-shared")
}

dependencies {
    implementation("jakarta.persistence:jakarta.persistence-api");
    implementation("jakarta.transaction:jakarta.transaction-api");
    
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")
    
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.security:spring-security-acl")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}