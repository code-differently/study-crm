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
includeBuild("../../common/common-domain")
includeBuild("../contact-service-domain")
includeBuild("../contact-service-persistence")
includeBuild("../contact-service-api-web")

rootProject.name = "contact-service-web"
include("contact-web-lib")
