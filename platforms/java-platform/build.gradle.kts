plugins {
    id("java-platform")
}

group = "com.codedifferently.studycrm.platform"

// allow the definition of dependencies to other platforms like the Spring Boot BOM
javaPlatform.allowDependencies()

val springCloudVersion: String by project
val springBootVersion: String by project
val springCloudContractDependenciesVersion: String by project
val eventuatePlatformVersion: String by project
val springDocOpenApiUiVersion: String by project
val oktaSpringBootStarterVersion: String by project

dependencies {
    api(platform("io.eventuate.platform:eventuate-platform-dependencies:$eventuatePlatformVersion"))

    api(platform("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"))
    api(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))
    api(platform("org.springframework.cloud:spring-cloud-contract-dependencies:$springCloudContractDependenciesVersion")) 

    api(platform("org.springdoc:springdoc-openapi-ui:$springDocOpenApiUiVersion")) 

    api(platform("com.okta.spring:okta-spring-boot-starter:$oktaSpringBootStarterVersion"))  
}