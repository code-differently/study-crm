plugins {
    id("com.codedifferently.studycrm.spring-web-library")
}

group = "com.codedifferently.studycrm.entity-service.web"

dependencies {
    implementation("com.codedifferently.studycrm.common.domain:common-domain-lib")
    implementation("com.codedifferently.studycrm.common.web:common-web-lib")
    implementation("com.codedifferently.studycrm.entity-service.domain:entity-domain-lib")
    implementation("com.codedifferently.studycrm.entity-service.layout:entity-layout-lib")
    implementation("com.codedifferently.studycrm.entity-service.persistence:entity-persistence-lib")
    implementation("com.codedifferently.studycrm.entity-service.api.web:entity-api-web-lib")
    
	implementation("org.springframework.security:spring-security-config")
    implementation("org.springframework.security:spring-security-oauth2-jose")
    implementation("org.springframework.security:spring-security-oauth2-resource-server")
}

testing {
    suites {
        val integrationTest by getting(JvmTestSuite::class)  {
            dependencies {
                implementation("com.codedifferently.studycrm.common.domain:common-domain-lib")
                implementation("com.codedifferently.studycrm.common.web:common-web-lib")
                implementation("com.codedifferently.studycrm.entity-service.domain:entity-domain-lib")
                implementation("com.codedifferently.studycrm.entity-service.layout:entity-layout-lib")
                implementation("com.codedifferently.studycrm.entity-service.api.web:entity-api-web-lib")
                implementation("org.springframework.security:spring-security-acl")
            }
        }
    }
}