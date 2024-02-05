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

sourceSets {
    create("integrationTest") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

val integrationTestImplementation by configurations.getting {
    extendsFrom(configurations.implementation.get())
}
val integrationTestRuntimeOnly by configurations.getting

configurations["integrationTestRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())

val integrationTest = task<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"

    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    shouldRunAfter("test")

    useJUnitPlatform()

    testLogging {
        events("passed")
    }

    finalizedBy("jacocoIntegrationTestReport")
}

dependencies {
    integrationTestImplementation(platform("com.codedifferently.studycrm.platform:java-test-platform"))
    integrationTestImplementation("org.junit.jupiter:junit-jupiter")
    integrationTestImplementation("org.mockito:mockito-core")
    integrationTestImplementation("org.springframework.boot:spring-boot-starter-test")
    integrationTestImplementation("org.springframework.security:spring-security-test")
    integrationTestRuntimeOnly("com.h2database:h2")
    integrationTestRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.register<JacocoReport>("jacocoIntegrationTestReport") {
    executionData(tasks.named("integrationTest").get())
    sourceSets(sourceSets["integrationTest"])

    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

tasks.jacocoTestReport { 
    dependsOn("jacocoIntegrationTestReport")
}

tasks.check { 
    dependsOn(integrationTest)
    dependsOn("jacocoIntegrationTestReport")
}