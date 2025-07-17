package com.otus.otuskotlin.groschenberry.app.kafka

import com.otus.otuskotlin.groschenberry.common.GrschbrContext

/**
 * Интерфейс стратегии для обслуживания версии API
 */
interface IConsumerStrategy {
    /**
     * Топики, для которых применяется стратегия
     */
    fun topics(config: AppKafkaConfig): InputOutputTopics
    /**
     * Сериализатор для версии API
     */
    fun serialize(source: GrschbrContext): String
    /**
     * Десериализатор для версии API
     */
    fun deserialize(value: String, target: GrschbrContext)
}
