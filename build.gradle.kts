import com.avast.gradle.dockercompose.ComposeExtension

plugins {
    `kotlin-dsl`
    id("com.codedifferently.studycrm.java-shared")
    id("jacoco-report-aggregation")
    id("com.avast.gradle.docker-compose") version "0.17.6"
}

repositories {
    mavenCentral()
}

val eventuateCommonImageVersion: String by project
val eventuateCdcImageVersion: String by project
val eventuateMessagingKafkaImageVersion: String by project

configure<ComposeExtension> {
    includeDependencies.set(true)
    environment.put("EVENTUATE_COMMON_VERSION", eventuateCommonImageVersion)
    environment.put("EVENTUATE_CDC_VERSION", eventuateCdcImageVersion)
    environment.put("EVENTUATE_MESSAGING_KAFKA_IMAGE_VERSION", eventuateMessagingKafkaImageVersion)
    environment.put("EVENTUATE_OUTBOX_TABLES", "8")

    createNested("infrastructure").apply {
        setProjectName(null)
        useComposeFiles.set(listOf("docker-compose.yaml"))
        startedServices.set(listOf("zipkin", "zookeeper", "kafka", "auth-service-mysql", "contact-service-mysql", "organization-service-mysql", "cdc-service"))
    }

    createNested("studycrm").apply {
        setProjectName(null)
        environment.putAll(mapOf("TAGS" to "feature-test,local"))
        useComposeFiles.set(listOf("docker-compose.yaml"))
        startedServices.set(listOf("zipkin", "zookeeper", "kafka", "auth-service", "auth-service-mysql", "contact-service", "contact-service-mysql", "organization-service", "organization-service-mysql", "api-gateway", "cdc-service"))
    }
}

tasks.register("buildAndRunInfrastructure") {
    dependsOn("infrastructureComposeUp")
}

tasks.register("buildAndRunServices") {
    dependsOn(gradle.includedBuild("auth-service-main").task(":auth-main-app:build"));
    dependsOn(gradle.includedBuild("contact-service-main").task(":contact-main-app:build"));
    dependsOn(gradle.includedBuild("organization-service-main").task(":organization-main-app:build"));
    dependsOn("studycrmComposeUp")
}

dependencies {
    implementation("com.codedifferently.studycrm.auth-service.main:auth-main-app")
    implementation("com.codedifferently.studycrm.contact-service.main:contact-main-app")
    implementation("com.codedifferently.studycrm.organization-service.main:organization-main-app")

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()

    dependsOn(gradle.includedBuild("common-domain").task(":common-domain-lib:test"))
    dependsOn(gradle.includedBuild("common-web").task(":common-web-lib:test"))

    dependsOn(gradle.includedBuild("auth-service-api-messaging").task(":auth-api-messaging-lib:test"))
    dependsOn(gradle.includedBuild("auth-service-api-web").task(":auth-api-web-lib:test"))
    dependsOn(gradle.includedBuild("auth-service-domain").task(":auth-domain-lib:test"))
    dependsOn(gradle.includedBuild("auth-service-main").task(":auth-main-app:test"))
    dependsOn(gradle.includedBuild("auth-service-messaging").task(":auth-messaging-lib:test"))
    dependsOn(gradle.includedBuild("auth-service-persistence").task(":auth-persistence-lib:test"))
    dependsOn(gradle.includedBuild("auth-service-web").task(":auth-web-lib:test"))

    dependsOn(gradle.includedBuild("contact-service-api-web").task(":contact-api-web-lib:test"))
    dependsOn(gradle.includedBuild("contact-service-domain").task(":contact-domain-lib:test"))
    dependsOn(gradle.includedBuild("contact-service-main").task(":contact-main-app:test"))
    dependsOn(gradle.includedBuild("contact-service-persistence").task(":contact-persistence-lib:test"))
    dependsOn(gradle.includedBuild("contact-service-web").task(":contact-web-lib:test"))

    dependsOn(gradle.includedBuild("organization-service-api-messaging").task(":organization-api-messaging-lib:test"))
    dependsOn(gradle.includedBuild("organization-service-api-web").task(":organization-api-web-lib:test"))
    dependsOn(gradle.includedBuild("organization-service-domain").task(":organization-domain-lib:test"))
    dependsOn(gradle.includedBuild("organization-service-main").task(":organization-main-app:test"))
    dependsOn(gradle.includedBuild("organization-service-persistence").task(":organization-persistence-lib:test"))
    dependsOn(gradle.includedBuild("organization-service-sagas").task(":organization-sagas-lib:test"))
    dependsOn(gradle.includedBuild("organization-service-web").task(":organization-web-lib:test"))

    finalizedBy(tasks.jacocoTestReport)
}

tasks.spotlessCheck {
    dependsOn(gradle.includedBuild("common-domain").task(":common-domain-lib:spotlessCheck"))
    dependsOn(gradle.includedBuild("common-web").task(":common-web-lib:spotlessCheck"))

    dependsOn(gradle.includedBuild("auth-service-api-messaging").task(":auth-api-messaging-lib:spotlessCheck"))
    dependsOn(gradle.includedBuild("auth-service-api-web").task(":auth-api-web-lib:spotlessCheck"))
    dependsOn(gradle.includedBuild("auth-service-domain").task(":auth-domain-lib:spotlessCheck"))
    dependsOn(gradle.includedBuild("auth-service-main").task(":auth-main-app:spotlessCheck"))
    dependsOn(gradle.includedBuild("auth-service-messaging").task(":auth-messaging-lib:spotlessCheck"))
    dependsOn(gradle.includedBuild("auth-service-persistence").task(":auth-persistence-lib:spotlessCheck"))
    dependsOn(gradle.includedBuild("auth-service-web").task(":auth-web-lib:spotlessCheck"))

    dependsOn(gradle.includedBuild("contact-service-api-web").task(":contact-api-web-lib:spotlessCheck"))
    dependsOn(gradle.includedBuild("contact-service-domain").task(":contact-domain-lib:spotlessCheck"))
    dependsOn(gradle.includedBuild("contact-service-main").task(":contact-main-app:spotlessCheck"))
    dependsOn(gradle.includedBuild("contact-service-persistence").task(":contact-persistence-lib:spotlessCheck"))
    dependsOn(gradle.includedBuild("contact-service-web").task(":contact-web-lib:spotlessCheck"))

    dependsOn(gradle.includedBuild("organization-service-api-messaging").task(":organization-api-messaging-lib:spotlessCheck"))
    dependsOn(gradle.includedBuild("organization-service-api-web").task(":organization-api-web-lib:spotlessCheck"))
    dependsOn(gradle.includedBuild("organization-service-domain").task(":organization-domain-lib:spotlessCheck"))
    dependsOn(gradle.includedBuild("organization-service-main").task(":organization-main-app:spotlessCheck"))
    dependsOn(gradle.includedBuild("organization-service-persistence").task(":organization-persistence-lib:spotlessCheck"))
    dependsOn(gradle.includedBuild("organization-service-sagas").task(":organization-sagas-lib:spotlessCheck"))
    dependsOn(gradle.includedBuild("organization-service-web").task(":organization-web-lib:spotlessCheck"))
}

tasks.spotlessApply {
    dependsOn(gradle.includedBuild("common-domain").task(":common-domain-lib:spotlessApply"))
    dependsOn(gradle.includedBuild("common-web").task(":common-web-lib:spotlessApply"))

    dependsOn(gradle.includedBuild("auth-service-api-messaging").task(":auth-api-messaging-lib:spotlessApply"))
    dependsOn(gradle.includedBuild("auth-service-api-web").task(":auth-api-web-lib:spotlessApply"))
    dependsOn(gradle.includedBuild("auth-service-domain").task(":auth-domain-lib:spotlessApply"))
    dependsOn(gradle.includedBuild("auth-service-main").task(":auth-main-app:spotlessApply"))
    dependsOn(gradle.includedBuild("auth-service-messaging").task(":auth-messaging-lib:spotlessApply"))
    dependsOn(gradle.includedBuild("auth-service-persistence").task(":auth-persistence-lib:spotlessApply"))
    dependsOn(gradle.includedBuild("auth-service-web").task(":auth-web-lib:spotlessApply"))

    dependsOn(gradle.includedBuild("contact-service-api-web").task(":contact-api-web-lib:spotlessApply"))
    dependsOn(gradle.includedBuild("contact-service-domain").task(":contact-domain-lib:spotlessApply"))
    dependsOn(gradle.includedBuild("contact-service-main").task(":contact-main-app:spotlessApply"))
    dependsOn(gradle.includedBuild("contact-service-persistence").task(":contact-persistence-lib:spotlessApply"))
    dependsOn(gradle.includedBuild("contact-service-web").task(":contact-web-lib:spotlessApply"))

    dependsOn(gradle.includedBuild("organization-service-api-messaging").task(":organization-api-messaging-lib:spotlessApply"))
    dependsOn(gradle.includedBuild("organization-service-api-web").task(":organization-api-web-lib:spotlessApply"))
    dependsOn(gradle.includedBuild("organization-service-domain").task(":organization-domain-lib:spotlessApply"))
    dependsOn(gradle.includedBuild("organization-service-main").task(":organization-main-app:spotlessApply"))
    dependsOn(gradle.includedBuild("organization-service-persistence").task(":organization-persistence-lib:spotlessApply"))
    dependsOn(gradle.includedBuild("organization-service-sagas").task(":organization-sagas-lib:spotlessApply"))
    dependsOn(gradle.includedBuild("organization-service-web").task(":organization-web-lib:spotlessApply"))
}

tasks.check {
    dependsOn(tasks.named<JacocoReport>("testCodeCoverageReport")) 
}