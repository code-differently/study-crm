dependencyResolutionManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

includeBuild("../platforms")

rootProject.name = "build-logic"
include("included-build")
include("java-shared")
include("java-library")
include("java-web-application")
include("node-application")
