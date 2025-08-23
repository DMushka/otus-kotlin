plugins {
    id("build-kmp")
}

kotlin {
    sourceSets {
        all { languageSettings.optIn("kotlin.RequiresOptIn") }

        commonMain {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(libs.cor)

                implementation(projects.groschenberryCommon)
                implementation(projects.groschenberryStubs)
                implementation(projects.groschenberryApiLog)
                implementation(projects.groschenberryAuth)
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                api(libs.coroutines.test)
                implementation(projects.groschenberryApiLog)
                implementation(projects.groschenberryRepoTests)
                implementation(projects.groschenberryRepoInmemory)
            }
        }
        jvmMain {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        jvmTest {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
