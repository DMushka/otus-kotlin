package com.otus.otuskotlin.groschenberry.app.kafka

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Test
import com.otus.otuskotlin.groschenberry.api.v1.apiV1BasicRequestSerialize
import com.otus.otuskotlin.groschenberry.api.v1.apiV1BasicResponseDeserialize
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBCreateObject
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBCreateRequest
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBCreateResponse
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBDebug
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBRequestDebugStubs
import com.otus.otuskotlin.groschenberry.api.v1.models.CIRequestDebugMode
import java.util.*
import kotlin.test.assertEquals


class KafkaControllerTest {
    @Test
    fun runKafka() {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer(true, StringSerializer(), StringSerializer())
        val config = AppKafkaConfig()
        val inputTopic = config.kafkaTopicInBasicV1
        val outputTopic = config.kafkaTopicOutBasicV1

        val app = AppKafkaConsumer(config, listOf(ConsumerStrategyBasic()), consumer = consumer, producer = producer)
        consumer.schedulePollTask {
            consumer.rebalance(Collections.singletonList(TopicPartition(inputTopic, 0)))
            consumer.addRecord(
                ConsumerRecord(
                    inputTopic,
                    PARTITION,
                    0L,
                    "test-1",
                    apiV1BasicRequestSerialize(
                        CIBCreateRequest(
                            cib = CIBCreateObject(
                                title = "Монета 1",
                                description = "some testing cib to check them all",
                            ),
                            debug = CIBDebug(
                                mode = CIRequestDebugMode.STUB,
                                stub = CIBRequestDebugStubs.SUCCESS,
                            ),
                        ),
                    )
                )
            )
            app.close()
        }

        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
        val tp = TopicPartition(inputTopic, PARTITION)
        startOffsets[tp] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        app.start()

        val message = producer.history().first()
        val result = apiV1BasicResponseDeserialize<CIBCreateResponse>(message.value())
        assertEquals(outputTopic, message.topic())
        assertEquals("Монета 1", result.cib?.title)
    }

    companion object {
        const val PARTITION = 0
    }
}