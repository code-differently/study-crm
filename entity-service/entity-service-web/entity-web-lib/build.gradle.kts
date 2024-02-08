plugins {
    id("com.codedifferently.studycrm.spring-web-library")
}

group = "com.codedifferently.studycrm.entity-service.web"

dependencies {
    implementation("com.codedifferently.studycrm.common.domain:common-domain-lib")
    implementation("com.codedifferently.studycrm.entity-service.domain:entity-domain-lib")
    implementation("com.codedifferently.studycrm.entity-service.persistence:entity-persistence-lib")
    implementation("com.codedifferently.studycrm.entity-service.api.web:entity-api-web-lib")
}

testing {
    suites {
        val integrationTest by getting(JvmTestSuite::class)  {
            dependencies {
                implementation("com.codedifferently.studycrm.common.domain:common-domain-lib")
                implementation("com.codedifferently.studycrm.entity-service.domain:entity-domain-lib")
                implementation("com.codedifferently.studycrm.entity-service.api.web:entity-api-web-lib")
            }
        }
    }
}