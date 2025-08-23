import io.ktor.plugin.features.*
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink

plugins {
    alias(libs.plugins.kotlinx.serialization)
    id("build-kmp")
    alias(libs.plugins.ktor)
    alias(libs.plugins.palantir.docker)
}

application {
    mainClass.set("io.ktor.server.cio.EngineMain")
}

ktor {
    configureNativeImage(project)
    docker {
        localImageName.set("${project.name}-jvm")
        imageTag.set(project.version.toString())
        jreVersion.set(JavaVersion.VERSION_21)
    }
}

jib {
    container.mainClass = application.mainClass.get()
}

docker {
    name = "${project.name}-x64:${project.version}"

    // Файлы для Docker-контекста
    files(
        file("src/commonMain/resources/application.yml"),
    )

    // Путь к Dockerfile.X64 (если не в корне)
    setDockerfile(file("Dockerfile.X64"))

    // Аргументы сборки
    buildArgs(mapOf(
        "APP_VERSION" to project.version.toString()
    ))

    // Лейблы
    labels(mapOf(
        "maintainer" to "dev@example.com"
    ))
}

kotlin {
    // !!! Обязательно. Иначе не проходит сборка толстых джанриков в shadowJar
    jvm { withJava() }
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries {
            executable {
                entryPoint = "com.otus.otuskotlin.groschenberry.app.ktor.main"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(libs.ktor.server.core)
                implementation(libs.ktor.server.cio)
                implementation(libs.ktor.server.cors)
                implementation(libs.ktor.server.yaml)
                implementation(libs.ktor.server.negotiation)
                implementation(libs.ktor.server.headers.response)
                implementation(libs.ktor.server.headers.caching)

//                // Для того, чтоб получать содержимое запроса более одного раза
//                В Application.main добавить `install(DoubleReceive)`
//                implementation("io.ktor:ktor-server-double-receive:${libs.versions.ktor.get()}")

                implementation(projects.groschenberryCommon)
                implementation(projects.groschenberryAppCommon)
                implementation(projects.groschenberryBiz)

                // v2 api
                implementation(projects.groschenberryApiV1Kmp)

                // Stubs
                implementation(projects.groschenberryStubs)

                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.ktor.serialization.json)

                // DB
                implementation(libs.uuid)
                implementation(projects.groschenberryCommon)
                implementation(projects.groschenberryRepoStubs)
                implementation(projects.groschenberryRepoInmemory)

                // logging
                implementation(projects.groschenberryApiLog)
                implementation("com.otus.otuskotlin.groschenberry.libs:groschenberry-lib-logging-common")
                implementation("com.otus.otuskotlin.groschenberry.libs:groschenberry-lib-logging-kermit")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                // DB
                implementation(projects.groschenberryRepoCommon)

                implementation(libs.ktor.server.test)
                implementation(libs.ktor.client.negotiation)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))

                // jackson
                implementation(libs.ktor.serialization.jackson)
                implementation(libs.ktor.server.calllogging)
                implementation(libs.ktor.server.headers.default)

                implementation(libs.logback)

                // transport models
                implementation(projects.groschenberryApiV1Kmp)

                // logging
                implementation("com.otus.otuskotlin.groschenberry.libs:groschenberry-lib-logging-logback")

                implementation(projects.groschenberryRepoPgjvm)
                implementation(libs.testcontainers.postgres)
                implementation(libs.testcontainers.core)
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

        linuxX64Main {
            dependencies {
                //implementation(projects.groschenberryRepoPgjvm)
            }
        }
    }
}

tasks {
    shadowJar {
        isZip64 = true
    }
    distTar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
    distZip {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    // Если ошибка: "Entry application.yaml is a duplicate but no duplicate handling strategy has been set."
    // Возникает из-за наличия файлов как в common, так и в jvm платформе
    withType(ProcessResources::class) {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    val linkReleaseExecutableLinuxX64 by getting(KotlinNativeLink::class)
    val nativeFileX64 = linkReleaseExecutableLinuxX64.binary.outputFile
    val linuxX64ProcessResources by getting(ProcessResources::class)
    dockerPrepare {
        dependsOn(linkReleaseExecutableLinuxX64)
        dependsOn(linuxX64ProcessResources)
        group = "docker"
        this.destinationDir
        doFirst {
            copy {
                from(nativeFileX64)
                from(linuxX64ProcessResources.destinationDir)
                into(this@dockerPrepare.destinationDir)
            }
        }
    }
}
