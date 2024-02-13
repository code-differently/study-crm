plugins {
    id("com.codedifferently.studycrm.spring-library")
}

group = "com.codedifferently.studycrm.auth-service.messaging"

dependencies {
    implementation("com.codedifferently.studycrm.common.domain:common-domain-lib")
    implementation("com.codedifferently.studycrm.auth-service.domain:auth-domain-lib")
    implementation("com.codedifferently.studycrm.auth-service.api.messaging:auth-api-messaging-lib")

    implementation("org.springframework.security:spring-security-crypto");

    implementation("io.eventuate.tram.core:eventuate-tram-spring-producer-jdbc")
    implementation("io.eventuate.tram.core:eventuate-tram-spring-consumer-kafka")
    implementation("io.eventuate.tram.core:eventuate-tram-spring-optimistic-locking")
    implementation("io.eventuate.tram.sagas:eventuate-tram-sagas-spring-in-memory")
    implementation("io.eventuate.tram.sagas:eventuate-tram-sagas-spring-participant")
}