plugins {
    id("java-platform")
}

group = "com.codedifferently.studycrm.platform"

// allow the definition of dependencies to other platforms like the JUnit 5 BOM
javaPlatform.allowDependencies()

val mockitoVersion: String by project

dependencies {
    api(platform("org.junit:junit-bom:5.7.1"))

    api(platform("org.mockito:mockito-core:$mockitoVersion"))
}
