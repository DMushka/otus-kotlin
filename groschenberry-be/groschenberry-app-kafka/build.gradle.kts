plugins {
    application
    id("build-jvm")
    alias(libs.plugins.muschko.java)
}

application {
    mainClass.set("com.otus.otuskotlin.groschenberry.app.kafka.MainKt")
}

docker {
    javaApplication {
        baseImage.set("openjdk:17.0.2-slim")
    }
}

dependencies {
    implementation(libs.kafka.client)
    implementation(libs.coroutines.core)
    implementation(libs.kotlinx.atomicfu)

    implementation("com.otus.otuskotlin.groschenberry.libs:groschenberry-lib-logging-logback")

    implementation(project(":groschenberry-app-common"))

    // transport models
    implementation(project(":groschenberry-common"))
    implementation(project(":groschenberry-api-v1-kmp"))
    // logic
    implementation(project(":groschenberry-biz"))

    testImplementation(kotlin("test-junit"))
}
