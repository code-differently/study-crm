plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(platform("com.simplecrm.platform:plugins-platform"))
    implementation(project(":java-shared"))
}