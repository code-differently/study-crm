plugins {
    id("com.codedifferently.studycrm.java-library")
}

group = "com.codedifferently.studycrm.auth-service.messaging"

dependencies {
    implementation("com.codedifferently.studycrm.common.domain:common-domain-lib")
    implementation("com.codedifferently.studycrm.auth-service.domain:auth-domain-lib")
    implementation("com.codedifferently.studycrm.auth-service.api.messaging:auth-api-messaging-lib")

    implementation("io.eventuate.tram.core:eventuate-tram-spring-flyway")
    runtimeOnly("io.eventuate.tram.sagas:eventuate-tram-sagas-spring-flyway")

    implementation("io.eventuate.tram.core:eventuate-tram-spring-jdbc-kafka")
    implementation("io.eventuate.tram.sagas:eventuate-tram-sagas-spring-participant-starter")
    implementation("io.eventuate.tram.core:eventuate-tram-spring-optimistic-locking")
}