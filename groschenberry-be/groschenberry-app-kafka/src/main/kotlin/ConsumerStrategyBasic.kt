package com.otus.otuskotlin.groschenberry.app.kafka

import com.otus.otuskotlin.groschenberry.api.v1.apiV1BasicRequestDeserialize
import com.otus.otuskotlin.groschenberry.api.v1.apiV1BasicResponseSerialize
import com.otus.otuskotlin.groschenberry.api.v1.models.IBasicRequest
import com.otus.otuskotlin.groschenberry.api.v1.mappers.fromTransport
import com.otus.otuskotlin.groschenberry.api.v1.mappers.toTransportCIB
import com.otus.otuskotlin.groschenberry.common.GrschbrContext

class ConsumerStrategyBasic : IConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInBasicV1, config.kafkaTopicOutBasicV1)
    }

    override fun serialize(source: GrschbrContext): String {
        return apiV1BasicResponseSerialize(source.toTransportCIB())
    }

    override fun deserialize(value: String, target: GrschbrContext) {
        val request: IBasicRequest = apiV1BasicRequestDeserialize(value)
        target.fromTransport(request)
    }
}
