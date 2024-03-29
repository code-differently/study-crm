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
includeBuild("../../common/common-web")
includeBuild("../organization-service-domain")
includeBuild("../organization-service-persistence")
includeBuild("../organization-service-api-web")
includeBuild("../organization-service-sagas")

rootProject.name = "organization-service-web"
include("organization-web-lib")
