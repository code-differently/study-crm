import com.avast.gradle.dockercompose.ComposeExtension

plugins {
    `kotlin-dsl`
    id("com.codedifferently.studycrm.java-shared")
    id("com.avast.gradle.docker-compose") version "0.17.6"
    id("jacoco-report-aggregation")
    id("test-report-aggregation")
}

repositories {
    mavenCentral()
}

dependencies {
    // Load bearing deps needed to aggregate coverage reports.
    jacocoAggregation("com.codedifferently.studycrm.auth-service.main:auth-main-app")
    jacocoAggregation("com.codedifferently.studycrm.entity-service.main:entity-main-app")
    jacocoAggregation("com.codedifferently.studycrm.organization-service.main:organization-main-app")
}

val eventuateCommonImageVersion: String by project
val eventuateCdcImageVersion: String by project
val eventuateMessagingKafkaImageVersion: String by project
val infrastructureServices = listOf("api-gateway", "zipkin", "zookeeper", "kafka", "auth-service-mysql", "entity-service-mysql", "organization-service-mysql", "cdc-service")

configure<ComposeExtension> {
    includeDependencies.set(true)
    environment.put("EVENTUATE_COMMON_VERSION", eventuateCommonImageVersion)
    environment.put("EVENTUATE_CDC_VERSION", eventuateCdcImageVersion)
    environment.put("EVENTUATE_MESSAGING_KAFKA_IMAGE_VERSION", eventuateMessagingKafkaImageVersion)
    environment.put("EVENTUATE_OUTBOX_TABLES", "8")

    createNested("barebones").apply {
        setProjectName(null)
        useComposeFiles.set(listOf("docker-compose.yaml"))
        startedServices.set(listOf("api-gateway", "entity-service-mysql"))
    }

    createNested("infrastructure").apply {
        setProjectName(null)
        useComposeFiles.set(listOf("docker-compose.yaml"))
        startedServices.set(infrastructureServices)
    }

    createNested("services").apply {
        setProjectName(null)
        environment.putAll(mapOf("TAGS" to "feature-test,local"))
        useComposeFiles.set(listOf("docker-compose.yaml"))
        startedServices.set(listOf(*infrastructureServices.toTypedArray(), "auth-service", "entity-service", "organization-service", "api-gateway"))
    }
}

tasks.register("startBarebones") {
    dependsOn("barebonesComposeUp")
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

reporting {
    reports {        
        val testFullCodeCoverageReport by creating(JacocoCoverageReport::class) {
            testType = TestSuiteType.PERFORMANCE_TEST
        }
    }
}

tasks.named<JacocoReport>("testFullCodeCoverageReport") {
    description = "Build a full test coverage report including test and integrationTest results"
    dependsOn( "testCodeCoverageReport", "integrationTestCodeCoverageReport" )
    executionData.setFrom(fileTree( project.rootDir.absolutePath ).include( "**/build/jacoco/*.exec" ))

    reports {
        xml.required = true
        html.required = true
    }
}

tasks.register("coverage") {
    dependsOn(tasks.named("testFullCodeCoverageReport"))
}

// Ensure that we generate the coverage report for the repo.
tasks.check {
    dependsOn(tasks.named("coverage")) 
}