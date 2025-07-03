package com.otus.otuskotlin.groschenberry.app.kafka

fun main() {
    val config = AppKafkaConfig()
    val consumer = AppKafkaConsumer(config, listOf(ConsumerStrategyBasic(), ConsumerStrategyDetail()))
    consumer.start()
}
