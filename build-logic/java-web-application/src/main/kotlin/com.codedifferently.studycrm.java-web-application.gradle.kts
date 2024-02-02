plugins {
    id("com.codedifferently.studycrm.java-shared")
    id("org.springframework.boot")
}

dependencies {
    implementation("org.ehcache:ehcache:3.10.8") {
        capabilities {
            requireCapability("org.ehcache:ehcache-jakarta")
        }
    }

    implementation("javax.cache:cache-api:1.1.1")

    implementation("io.eventuate.tram.core:eventuate-tram-spring-logging")
    implementation("io.eventuate.tram.springcloudsleuth:eventuate-tram-spring-cloud-sleuth-tram-starter")

    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    implementation("org.springframework.cloud:spring-cloud-sleuth-zipkin")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui") 
}
