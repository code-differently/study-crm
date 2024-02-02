plugins {
    id("java")
    id("jacoco")
    id("eclipse")
    id("io.freefair.lombok")
    id("com.diffplug.spotless")
}

group = "com.codedifferently.studycrm"

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {

  format("misc", {
    // define the files to apply `misc` to
    target("*.gradle", ".gitattributes", ".gitignore")

    // define the steps to apply to those files
    trimTrailingWhitespace()
    indentWithTabs() // or spaces. Takes an integer argument if you don't like 4
    endWithNewline()
  })

  java {
    // don't need to set target, it is inferred from java

    // apply a specific flavor of google-java-format
    googleJavaFormat()
    // fix formatting of type annotations
    formatAnnotations()
  }
}

dependencies {
    implementation(platform("com.codedifferently.studycrm.platform:java-platform"))

    testImplementation(platform("com.codedifferently.studycrm.platform:java-test-platform"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("com.h2database:h2")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}
