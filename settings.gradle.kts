pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "groschenberry"

includeBuild("lessons")
includeBuild("groschenberry-be")
includeBuild("groschenberry-libs")
includeBuild("groschenberry-other")