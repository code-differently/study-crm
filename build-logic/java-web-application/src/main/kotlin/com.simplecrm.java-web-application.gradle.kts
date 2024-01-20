plugins {
    id("com.simplecrm.java-shared")
    id("org.springframework.boot")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("io.eventuate.tram.core:eventuate-tram-spring-logging")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    implementation("org.springframework.cloud:spring-cloud-sleuth-zipkin")
    implementation("io.eventuate.tram.springcloudsleuth:eventuate-tram-spring-cloud-sleuth-tram-starter")
    implementation("org.springdoc:springdoc-openapi-ui")
}
