import com.github.gradle.node.task.NodeTask

plugins {
    id("com.simplecrm.node-application")
}

tasks.register<NodeTask>("run") {
  script.set(file("src/main.js"))
}