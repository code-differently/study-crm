import com.github.gradle.node.npm.task.NpmTask

plugins {
    id("com.simplecrm.node-application")
}

tasks.npmInstall {
    nodeModulesOutputFilter {
        exclude("notExistingFile")
    }
}

tasks.register<NpmTask>("run") {
    dependsOn(tasks.npmInstall)
    npmCommand.set(listOf("run", "dev"))
}