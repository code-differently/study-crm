plugins {
    id("java")
    id("eclipse")
    id("io.freefair.lombok")
}

group = "com.codedifferently.studycrm"

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation(platform("com.codedifferently.studycrm.platform:java-platform"))

    testImplementation(platform("com.codedifferently.studycrm.platform:java-test-platform"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}
