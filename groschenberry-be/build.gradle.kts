plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.muschko.remote) apply false
    alias(libs.plugins.muschko.java) apply false
}

group = "com.otus.otuskotlin.groschenberry"
version = "0.0.1"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}

ext {
    val specDir = layout.projectDirectory.dir("../specs")
    set("spec-v1", specDir.file("specs-ci-v1.yaml").toString())
    set("spec-ci-log", specDir.file("specs-ci-log.yaml").toString())
}

tasks {
    arrayOf("build", "clean", "check").forEach { tsk ->
        register(tsk) {
            group = "build"
            dependsOn(subprojects.map { it.getTasksByName(tsk, false) })
        }
    }

    register("buildImages") {
        dependsOn(project("groschenberry-app-ktor").tasks.getByName("publishImageToLocalRegistry"))
        dependsOn(project("groschenberry-app-ktor").tasks.getByName("dockerBuildX64Image"))
    }
}