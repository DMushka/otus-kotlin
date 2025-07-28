package com.otus.otuskotlin.groschenberry.e2e.be.docker

import com.otus.otuskotlin.groschenberry.e2e.be.fixture.docker.AbstractDockerCompose

object KtorDockerCompose : AbstractDockerCompose(
    "app-ktor_1", 8080, "docker-compose-ktor.yml"
)
