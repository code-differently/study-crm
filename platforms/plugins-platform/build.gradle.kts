plugins {
    id("java-platform")
}

group = "com.codedifferently.studycrm.platform"

dependencies {
    constraints {
        api("org.springframework.boot:org.springframework.boot.gradle.plugin:2.7.8")
    }
}
