plugins {
    id("java-library")
    id("com.codedifferently.studycrm.java-shared")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
	runtimeOnly("org.springframework:spring-context-support")
}

testing {
    suites { 
        withType<JvmTestSuite> {
            dependencies { 
                implementation("org.springframework.boot:spring-boot-starter-data-jpa")
                implementation("org.springframework.boot:spring-boot-starter-test")
                implementation("org.springframework.security:spring-security-test")
            }
        }
    }
}