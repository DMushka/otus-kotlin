plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
}

group = "com.otus.otuskotlin.groschenberry"
version = "0.0.1"

repositories {
    mavenCentral()
}

subprojects {
    repositories {
        mavenCentral()
    }
    group = rootProject.group
    version = rootProject.version
}

tasks {
    register("clean") {
        group = "build"
        gradle.includedBuilds.forEach {
            dependsOn(it.task(":clean"))
        }
    }
    val buildMigrations: Task by creating {
        dependsOn(gradle.includedBuild("groschenberry-other").task(":buildImages"))
    }

    val buildImages: Task by creating {
        dependsOn(buildMigrations)
        dependsOn(gradle.includedBuild("groschenberry-be").task(":buildImages"))
    }

    create("check") {
        group = "verification"
        dependsOn(gradle.includedBuild("groschenberry-be").task(":check"))
    }
}
