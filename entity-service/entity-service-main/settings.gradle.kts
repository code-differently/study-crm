// == Define locations for build logic ==
pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    includeBuild("../../build-logic")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.8.0")
}

// == Define locations for components ==
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
includeBuild("../../platforms")
includeBuild("../../common/common-web")
includeBuild("../entity-service-web")

rootProject.name = "entity-service-main"
include("entity-main-app")
