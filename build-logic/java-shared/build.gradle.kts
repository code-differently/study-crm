plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(platform("com.codedifferently.studycrm.platform:plugins-platform"))
    implementation("io.freefair.gradle:lombok-plugin")
    implementation("com.diffplug.spotless:spotless-plugin-gradle")
    implementation("com.adarshr:gradle-test-logger-plugin")
}
