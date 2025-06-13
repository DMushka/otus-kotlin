@file:Suppress("unused")
package com.otus.otuskotlin.groschenberry.api.v1

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import com.otus.otuskotlin.groschenberry.api.v1.models.IBasicRequest
import com.otus.otuskotlin.groschenberry.api.v1.models.IBasicResponse

@OptIn(ExperimentalSerializationApi::class)
@Suppress("JSON_FORMAT_REDUNDANT_DEFAULT")
val apiV1Mapper = Json {
//    ignoreUnknownKeys = true
    allowTrailingComma = true
}

@Suppress("UNCHECKED_CAST")
fun <T : IBasicRequest> apiV2RequestDeserialize(json: String) =
    apiV1Mapper.decodeFromString<IBasicRequest>(json) as T

fun apiV2ResponseSerialize(obj: IBasicResponse): String =
    apiV1Mapper.encodeToString(IBasicResponse.serializer(), obj)

@Suppress("UNCHECKED_CAST")
fun <T : IBasicResponse> apiV2ResponseDeserialize(json: String) =
    apiV1Mapper.decodeFromString<IBasicResponse>(json) as T

inline fun <reified T : IBasicResponse> apiV2ResponseSimpleDeserialize(json: String) =
    apiV1Mapper.decodeFromString<T>(json)

@Suppress("unused")
fun apiV2RequestSerialize(obj: IBasicRequest): String =
    apiV1Mapper.encodeToString(IBasicRequest.serializer(), obj)

@Suppress("unused")
inline fun <reified T : IBasicRequest> apiV2RequestSimpleSerialize(obj: T): String =
    apiV1Mapper.encodeToString<T>(obj)
