plugins {
    id("java-platform")
}

group = "com.codedifferently.studycrm.platform"

// allow the definition of dependencies to other platforms like the Spring Boot BOM
javaPlatform.allowDependencies()

val ehcacheVersion: String by project
val eventuatePlatformVersion: String by project
val springBootVersion: String by project
val springCloudContractDependenciesVersion: String by project
val springCloudVersion: String by project
val springDocOpenApiUiVersion: String by project

dependencies {
    api(platform("io.eventuate.platform:eventuate-platform-dependencies:$eventuatePlatformVersion"))

    api(platform("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocOpenApiUiVersion"))
    
    api(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))
    api(platform("org.springframework.cloud:spring-cloud-contract-dependencies:$springCloudContractDependenciesVersion")) 
    api(platform("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"))
}