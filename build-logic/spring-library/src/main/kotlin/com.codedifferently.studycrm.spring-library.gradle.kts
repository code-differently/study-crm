plugins {
    id("java-library")
    id("com.codedifferently.studycrm.java-shared")
}

dependencies {
    implementation("jakarta.persistence:jakarta.persistence-api");
    implementation("jakarta.transaction:jakarta.transaction-api");
    
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.security:spring-security-acl")
}

testing {
    suites { 
        withType<JvmTestSuite> {
            dependencies { 
                implementation("org.springframework.boot:spring-boot-starter-test")
                implementation("org.springframework.security:spring-security-test")
            }
        }
    }
}