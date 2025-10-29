plugins {
    id("java")
    id("jvm-test-suite")
    id("jacoco")
    id("eclipse")
    id("io.freefair.lombok")
    id("com.diffplug.spotless")
    id("com.adarshr.test-logger")
}

group = "com.codedifferently.studycrm"

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {

  format("misc", {
    // define the files to apply `misc` to
    target("*.gradle", ".gitattributes", ".gitignore")

    // define the steps to apply to those files
    trimTrailingWhitespace()
    leadingSpacesToTabs()
    endWithNewline()
  })

  java {
    // don't need to set target, it is inferred from java

    // apply a specific flavor of google-java-format compatible with Java 21
    googleJavaFormat()
    // fix formatting of type annotations
    formatAnnotations()
  }
}

dependencies {
    implementation(platform("com.codedifferently.studycrm.platform:java-platform"))
}

testing {
    suites { 
        withType<JvmTestSuite> { 
            useJUnitJupiter()
            dependencies { 
                implementation(platform("com.codedifferently.studycrm.platform:java-test-platform"))
                implementation("org.assertj:assertj-core")
                implementation("org.mockito:mockito-junit-jupiter")
                runtimeOnly("com.h2database:h2")
            }
        }

        register<JvmTestSuite>("integrationTest") {
            testType = TestSuiteType.INTEGRATION_TEST
            
            dependencies {
                implementation(project())
                runtimeOnly(project())
            }

            targets { 
                all {
                    testTask.configure {
                        shouldRunAfter(testing.suites.named("test"))
                    }
                }
            }
        }
    }
}

tasks.named("check") { 
    dependsOn(testing.suites.named("integrationTest"))
}
