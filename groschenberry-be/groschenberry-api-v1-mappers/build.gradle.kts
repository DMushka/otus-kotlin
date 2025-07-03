plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(projects.groschenberryApiV1Kmp)
    implementation(projects.groschenberryCommon)

    testImplementation(kotlin("test-junit"))
    testImplementation(projects.groschenberryStubs)
}
