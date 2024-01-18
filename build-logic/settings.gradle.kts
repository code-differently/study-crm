dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        google()
    }
}

includeBuild("../platforms")

rootProject.name = "build-logic"
include("java-shared")
include("java-library")
include("java-web-application")
include("node-application")
