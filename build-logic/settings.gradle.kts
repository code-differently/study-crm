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
include("java-web-application")
include("node-application")
include("spring-library")
include("spring-web-library")
