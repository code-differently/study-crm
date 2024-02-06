plugins {
    id("java-platform")
}

group = "com.codedifferently.studycrm.platform"

// allow the definition of dependencies to other platforms like the JUnit 5 BOM
javaPlatform.allowDependencies()

val mockitoVersion: String by project
val springBootVersion: String by project
val junitBomVersion: String by project

dependencies {
    api(platform("org.junit:junit-bom:$junitBomVersion"))
    api(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))
    api(platform("org.mockito:mockito-core:$mockitoVersion"))
    api(platform("org.mockito:mockito-junit-jupiter:$mockitoVersion"))
}
