package com.otus.otuskotlin.groschenberry.e2e.be.docker

import com.otus.otuskotlin.groschenberry.e2e.be.fixture.docker.AbstractDockerCompose

object WiremockDockerCompose : AbstractDockerCompose(
    "app-wiremock", 8080, "docker-compose-wiremock.yml"
)
