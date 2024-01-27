plugins {
    id("java-platform")
}

group = "com.codedifferently.studycrm.platform"

val gradlePluginVersion: String by project
val lombokVersion: String by project

dependencies {
    constraints {
        api("org.springframework.boot:org.springframework.boot.gradle.plugin:${gradlePluginVersion}")
        api("io.freefair.gradle:lombok-plugin:${lombokVersion}")
    }
}
