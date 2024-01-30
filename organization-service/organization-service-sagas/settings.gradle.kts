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
includeBuild("../../auth-service/auth-service-api-messaging")
includeBuild("../organization-service-domain")
includeBuild("../organization-service-api-messaging")

rootProject.name = "organization-service-sagas"
include("organization-sagas-lib")
