tasks.register("buildAll") {
    subprojects.forEach {
        dependsOn(it.tasks.named("build"))
    }
}

tasks.register("testAll") {
    subprojects.forEach {
        dependsOn(it.tasks.named("test"))
    }
}

tasks.register("checkAll") {
    subprojects.forEach {
        dependsOn(it.tasks.named("check"))
    }
}

tasks.register("fixAll") {
    subprojects.forEach {
        dependsOn(it.tasks.named("spotlessApply"))
    }
}

tasks.register("cleanAll") {
    subprojects.forEach {
        dependsOn(it.tasks.named("clean"))
    }
}