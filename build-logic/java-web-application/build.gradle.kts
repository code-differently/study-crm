plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(platform("com.codedifferently.studycrm.platform:plugins-platform"))
    implementation(project(":java-shared"))
    implementation("org.springframework.boot:org.springframework.boot.gradle.plugin")
}