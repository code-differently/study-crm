plugins {
    id("com.codedifferently.studycrm.java-shared")
    id("org.springframework.boot")
}

dependencies {
    implementation("io.eventuate.tram.core:eventuate-tram-spring-logging")
    implementation("io.eventuate.tram.springcloudsleuth:eventuate-tram-spring-cloud-sleuth-tram-starter")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    implementation("org.springframework.cloud:spring-cloud-sleuth-zipkin")
    
    implementation("org.springdoc:springdoc-openapi-ui")

    implementation("com.okta.spring:okta-spring-boot-starter");
}
