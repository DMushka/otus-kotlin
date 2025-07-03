package com.otus.otuskotlin.groschenberry.app.kafka

import com.otus.otuskotlin.groschenberry.app.common.IGrschbrAppSettings
import com.otus.otuskotlin.groschenberry.biz.GrschbrCIProcessor
import com.otus.otuskotlin.groschenberry.common.GrschbrCorSettings
import com.otus.otuskotlin.groschenberry.logging.common.GrbLoggerProvider
import com.otus.otuskotlin.groschenberry.logging.jvm.grbLoggerLogback

class AppKafkaConfig(
    val kafkaHosts: List<String> = KAFKA_HOSTS,
    val kafkaGroupId: String = KAFKA_GROUP_ID,
    val kafkaTopicInBasicV1: String = KAFKA_TOPIC_IN_BASIC_V1,
    val kafkaTopicOutBasicV1: String = KAFKA_TOPIC_OUT_BASIC_V1,
    val kafkaTopicInDetailV1: String = KAFKA_TOPIC_IN_DETAIL_V1,
    val kafkaTopicOutDetailV1: String = KAFKA_TOPIC_OUT_DETAIL_V1,
    override val corSettings: GrschbrCorSettings = GrschbrCorSettings(
        loggerProvider = GrbLoggerProvider { grbLoggerLogback(it) }
    ),
    override val processor: GrschbrCIProcessor = GrschbrCIProcessor(corSettings),
): IGrschbrAppSettings {
    companion object {
        const val KAFKA_HOST_VAR = "KAFKA_HOSTS"
        const val KAFKA_TOPIC_IN_BASIC_V1_VAR = "KAFKA_TOPIC_IN_BASIC_V1"
        const val KAFKA_TOPIC_IN_DETAIL_V1_VAR = "KAFKA_TOPIC_IN_DETAIL_V1"
        const val KAFKA_TOPIC_OUT_BASIC_V1_VAR = "KAFKA_TOPIC_OUT_BASIC_V1"
        const val KAFKA_TOPIC_OUT_DETAIL_V1_VAR = "KAFKA_TOPIC_OUT_DETAIL_V1"
        const val KAFKA_GROUP_ID_VAR = "KAFKA_GROUP_ID"

        val KAFKA_HOSTS by lazy { (System.getenv(KAFKA_HOST_VAR) ?: "").split("\\s*[,; ]\\s*") }
        val KAFKA_GROUP_ID by lazy { System.getenv(KAFKA_GROUP_ID_VAR) ?: "groschenberry" }
        val KAFKA_TOPIC_IN_BASIC_V1 by lazy { System.getenv(KAFKA_TOPIC_IN_BASIC_V1_VAR) ?: "groschenberry-cib-v1-in" }
        val KAFKA_TOPIC_IN_DETAIL_V1 by lazy { System.getenv(KAFKA_TOPIC_IN_DETAIL_V1_VAR) ?: "groschenberry-cid-v1-in" }
        val KAFKA_TOPIC_OUT_BASIC_V1 by lazy { System.getenv(KAFKA_TOPIC_OUT_BASIC_V1_VAR) ?: "groschenberry-cib-v1-out" }
        val KAFKA_TOPIC_OUT_DETAIL_V1 by lazy { System.getenv(KAFKA_TOPIC_OUT_DETAIL_V1_VAR) ?: "groschenberry-cid-v1-out" }
    }
}
