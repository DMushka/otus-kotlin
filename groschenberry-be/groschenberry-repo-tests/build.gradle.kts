plugins {
    id("build-kmp")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(kotlin("test-common"))
                api(kotlin("test-annotations-common"))

                api(libs.coroutines.core)
                api(libs.coroutines.test)
                implementation(projects.groschenberryCommon)
                implementation(projects.groschenberryRepoCommon)
                implementation(projects.groschenberryStubs)
            }
        }
        commonTest {
            dependencies {
                implementation(projects.groschenberryStubs)
            }
        }
        jvmMain {
            dependencies {
                api(kotlin("test-junit"))
            }
        }
    }
}

tasks {
    register("test") {
        group = "build"
        gradle.includedBuilds.forEach {
            dependsOn(it.task(":test"))
        }
    }
}