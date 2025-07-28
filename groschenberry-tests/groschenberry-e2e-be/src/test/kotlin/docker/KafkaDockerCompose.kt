package com.otus.otuskotlin.groschenberry.e2e.be.docker

import com.otus.otuskotlin.groschenberry.e2e.be.fixture.docker.AbstractDockerCompose

object KafkaDockerCompose : AbstractDockerCompose(
    "kafka_1", 9091, "docker-compose-kafka.yml"
)
