package com.otus.otuskotlin.groschenberry.app.kafka

import com.otus.otuskotlin.groschenberry.api.v1.apiV1DetailRequestDeserialize
import com.otus.otuskotlin.groschenberry.api.v1.apiV1DetailResponseSerialize
import com.otus.otuskotlin.groschenberry.api.v1.models.IDetailRequest
import com.otus.otuskotlin.groschenberry.api.v1.mappers.fromTransport
import com.otus.otuskotlin.groschenberry.api.v1.mappers.toTransportCIB
import com.otus.otuskotlin.groschenberry.api.v1.mappers.toTransportCID
import com.otus.otuskotlin.groschenberry.common.GrschbrContext

class ConsumerStrategyDetail : IConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInDetailV1, config.kafkaTopicOutDetailV1)
    }

    override fun serialize(source: GrschbrContext): String {
        return apiV1DetailResponseSerialize(source.toTransportCID())
    }

    override fun deserialize(value: String, target: GrschbrContext) {
        val request: IDetailRequest = apiV1DetailRequestDeserialize(value)
        target.fromTransport(request)
    }
}
