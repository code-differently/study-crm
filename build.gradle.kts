import com.avast.gradle.dockercompose.ComposeExtension

plugins {
    `kotlin-dsl`
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

    createNested("mysqlinfrastructure").apply {
        setProjectName(null)
        useComposeFiles.set(listOf("docker-compose.yaml"))
        startedServices.set(listOf("zipkin", "zookeeper", "kafka", "contact-service-mysql"))
    }

    createNested("simplecrm").apply {
        setProjectName(null)
        environment.putAll(mapOf("TAGS" to "feature-test,local"))
        useComposeFiles.set(listOf("docker-compose.yaml"))
        startedServices.set(listOf("zipkin", "zookeeper", "kafka", "contact-service", "contact-service-mysql"))
    }
}

tasks.register("buildAndRunSqlInfrastructure") {
    dependsOn(gradle.includedBuild("contact-service-main").task(":app:build"));
    dependsOn("mysqlinfrastructureComposeUp")
}

tasks.register("buildAndRunServices") {
    dependsOn(gradle.includedBuild("contact-service-main").task(":app:build"));
    dependsOn("simplecrmComposeUp")
}
