plugins {
    id("com.codedifferently.studycrm.spring-library")
}

group = "com.codedifferently.studycrm.organization-service.sagas"

dependencies {
    implementation("com.codedifferently.studycrm.common.domain:common-domain-lib")
    implementation("com.codedifferently.studycrm.auth-service.api.messaging:auth-api-messaging-lib")
    implementation("com.codedifferently.studycrm.organization-service.api.messaging:organization-api-messaging-lib")
    implementation("com.codedifferently.studycrm.organization-service.api.web:organization-api-web-lib")
    implementation("com.codedifferently.studycrm.organization-service.domain:organization-domain-lib")

    implementation("io.eventuate.tram.core:eventuate-tram-spring-jdbc-kafka")
    implementation("io.eventuate.tram.core:eventuate-tram-spring-optimistic-locking")
    implementation("io.eventuate.tram.sagas:eventuate-tram-sagas-spring-orchestration-simple-dsl-starter")
}

testing {
    suites {
        val test by getting(JvmTestSuite::class)  {
            dependencies {
                implementation("io.eventuate.tram.sagas:eventuate-tram-sagas-spring-testing-support")
            }
        }
    }
}