plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("com.otus.otuskotlin.groschenberry:groschenberry-api-v1-kmp")
    implementation("com.otus.otuskotlin.groschenberry:groschenberry-stubs")

    testImplementation(libs.logback)
    testImplementation(libs.kermit)

    testImplementation(libs.bundles.kotest)

    testImplementation(libs.testcontainers.core)
    testImplementation(libs.coroutines.core)

    testImplementation(libs.ktor.client.core)
    testImplementation(libs.ktor.client.okhttp)

    testImplementation(libs.kotlinx.serialization.core)
    testImplementation(libs.kotlinx.serialization.json)
}

var severity: String = "MINOR"

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
//        dependsOn(gradle.includedBuild(":groschenberry-be").task(":groschenberry-app-ktor:publishImageToLocalRegistry"))
//        dependsOn(gradle.includedBuild(":groschenberry-app-kafka").task("dockerBuildImage"))
    }
}
