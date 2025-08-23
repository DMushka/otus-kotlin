package com.otus.otuskotlin.groschenberry.e2e.be.docker

import com.otus.otuskotlin.groschenberry.e2e.be.fixture.docker.AbstractDockerCompose

object KtorJvmKeycloakDockerCompose : AbstractDockerCompose(
    "envoy", 8080, "docker-compose-ktor-keycloak-jvm.yml"
)