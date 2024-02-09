plugins {
    id("com.codedifferently.studycrm.spring-web-library")
}

group = "com.codedifferently.studycrm.auth-service.web"

dependencies {
    implementation("com.codedifferently.studycrm.common.domain:common-domain-lib")
    implementation("com.codedifferently.studycrm.auth-service.domain:auth-domain-lib")
    implementation("com.codedifferently.studycrm.auth-service.persistence:auth-persistence-lib")
    implementation("com.codedifferently.studycrm.auth-service.api.web:auth-api-web-lib")

    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.security:spring-security-oauth2-authorization-server")
    implementation("org.springframework.security:spring-security-cas")
    implementation("org.springframework:spring-jdbc")
}

testing {
    suites {
        val integrationTest by getting(JvmTestSuite::class)  {
            dependencies {
                implementation("com.codedifferently.studycrm.common.web:common-web-lib")
                implementation("org.springframework.security:spring-security-oauth2-authorization-server")
            }
        }
    }
}