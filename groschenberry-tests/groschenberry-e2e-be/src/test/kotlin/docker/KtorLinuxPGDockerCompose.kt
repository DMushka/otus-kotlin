package com.otus.otuskotlin.groschenberry.e2e.be.docker

import com.otus.otuskotlin.groschenberry.e2e.be.fixture.docker.AbstractDockerCompose

object KtorLinuxPGDockerCompose : AbstractDockerCompose(
    "app-ktor",
    8080,
    "docker-compose-ktor-pg-linux.yml",
)
