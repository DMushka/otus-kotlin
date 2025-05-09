pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "lessons"

include("m1l1-first")
include("m1l3-func")
include("m1l4-oop")
include("m2l1-dsl")
include("m2l2-coroutines")

