plugins {
    id("java-platform")
}

group = "com.codedifferently.studycrm.platform"

// allow the definition of dependencies to other platforms like the JUnit 5 BOM
javaPlatform.allowDependencies()

val assertjVersion: String by project
val junitBomVersion: String by project
val mockitoVersion: String by project
val springBootVersion: String by project

dependencies {
    api(platform("org.assertj:assertj-core:$assertjVersion"))
    api(platform("org.junit:junit-bom:$junitBomVersion"))
    api(platform("org.mockito:mockito-core:$mockitoVersion"))
    api(platform("org.mockito:mockito-junit-jupiter:$mockitoVersion"))
    api(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))
}
