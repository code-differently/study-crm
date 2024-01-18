plugins {
    id("java-platform")
}

group = "com.simplecrm.platform"

// allow the definition of dependencies to other platforms like the Spring Boot BOM
javaPlatform.allowDependencies()

dependencies {
    api(platform("org.springframework.boot:spring-boot-dependencies:2.7.8"))
}