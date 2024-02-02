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

dependencies {
    // Load bearing deps needed to aggregate jacoco reports.
    implementation("com.codedifferently.studycrm.auth-service.main:auth-main-app")
    implementation("com.codedifferently.studycrm.contact-service.main:contact-main-app")
    implementation("com.codedifferently.studycrm.organization-service.main:organization-main-app")
}

val eventuateCommonImageVersion: String by project
val eventuateCdcImageVersion: String by project
val eventuateMessagingKafkaImageVersion: String by project
val infrastructureServices = listOf("zipkin", "zookeeper", "kafka", "auth-service-mysql", "contact-service-mysql", "organization-service-mysql", "cdc-service")

configure<ComposeExtension> {
    includeDependencies.set(true)
    environment.put("EVENTUATE_COMMON_VERSION", eventuateCommonImageVersion)
    environment.put("EVENTUATE_CDC_VERSION", eventuateCdcImageVersion)
    environment.put("EVENTUATE_MESSAGING_KAFKA_IMAGE_VERSION", eventuateMessagingKafkaImageVersion)
    environment.put("EVENTUATE_OUTBOX_TABLES", "8")

    createNested("infrastructure").apply {
        setProjectName(null)
        useComposeFiles.set(listOf("docker-compose.yaml"))
        startedServices.set(infrastructureServices)
    }

    createNested("services").apply {
        setProjectName(null)
        environment.putAll(mapOf("TAGS" to "feature-test,local"))
        useComposeFiles.set(listOf("docker-compose.yaml"))
        startedServices.set(listOf(*infrastructureServices.toTypedArray(), "auth-service", "contact-service", "organization-service", "api-gateway"))
    }
}

tasks.register("startInfrastructure") {
    dependsOn("infrastructureComposeUp")
}

tasks.register("start") {
    dependsOn("assemble");
    dependsOn("servicesComposeUp")
}

tasks.register("teardown") {
    dependsOn("servicesComposeDown")
}

// Exclude these projects from the repo build tasks.
var excludeProjects = listOf("build-logic", "platforms")

// Ensure the fix tasks exists for we can include in next loop.
tasks.register("fix") {}

// Setup repo build tasks
listOf("build", "test", "check", "fix", "clean", "assemble").forEach { taskName ->
    tasks.named(taskName) {

        dependsOn(
            gradle.includedBuilds
                .filter { !excludeProjects.contains(it.name) }
                .map { it.task(":${taskName}All") }
        )
    }
}

tasks.test {
    configure<JacocoTaskExtension> {
        isEnabled = true
        includes = listOf("**/lombok/**/*.class", "**/lombok/*.class")
    }
}

// Ensure that we generate the jacoco report for the repo.
tasks.check {
    dependsOn(tasks.named<JacocoReport>("testCodeCoverageReport")) 
}

tasks.register("coverage") {
    dependsOn("testCodeCoverageReport")
}