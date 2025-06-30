@file:Suppress("unused")
package com.otus.otuskotlin.groschenberry.api.v1

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import com.otus.otuskotlin.groschenberry.api.v1.models.IBasicRequest
import com.otus.otuskotlin.groschenberry.api.v1.models.IBasicResponse
import com.otus.otuskotlin.groschenberry.api.v1.models.IDetailRequest
import com.otus.otuskotlin.groschenberry.api.v1.models.IDetailResponse

@OptIn(ExperimentalSerializationApi::class)
@Suppress("JSON_FORMAT_REDUNDANT_DEFAULT")
val apiV1Mapper = Json {
//    ignoreUnknownKeys = true
    allowTrailingComma = true
}

@Suppress("UNCHECKED_CAST")
fun <T : IBasicRequest> apiV1BasicRequestDeserialize(json: String) =
    apiV1Mapper.decodeFromString<IBasicRequest>(json) as T

fun apiV1BasicResponseSerialize(obj: IBasicResponse): String =
    apiV1Mapper.encodeToString(IBasicResponse.serializer(), obj)

@Suppress("UNCHECKED_CAST")
fun <T : IBasicResponse> apiV1BasicResponseDeserialize(json: String) =
    apiV1Mapper.decodeFromString<IBasicResponse>(json) as T

inline fun <reified T : IBasicResponse> apiV1BasicResponseSimpleDeserialize(json: String) =
    apiV1Mapper.decodeFromString<T>(json)

@Suppress("unused")
fun apiV1BasicRequestSerialize(obj: IBasicRequest): String =
    apiV1Mapper.encodeToString(IBasicRequest.serializer(), obj)

@Suppress("unused")
inline fun <reified T : IBasicRequest> apiV1BasicRequestSimpleSerialize(obj: T): String =
    apiV1Mapper.encodeToString<T>(obj)

@Suppress("UNCHECKED_CAST")
fun <T : IDetailRequest> apiV1DetailRequestDeserialize(json: String) =
    apiV1Mapper.decodeFromString<IDetailRequest>(json) as T

fun apiV1DetailResponseSerialize(obj: IDetailResponse): String =
    apiV1Mapper.encodeToString(IDetailResponse.serializer(), obj)

@Suppress("UNCHECKED_CAST")
fun <T : IDetailResponse> apiV1DetailResponseDeserialize(json: String) =
    apiV1Mapper.decodeFromString<IDetailResponse>(json) as T

inline fun <reified T : IDetailResponse> apiV1DetailResponseSimpleDeserialize(json: String) =
    apiV1Mapper.decodeFromString<T>(json)

@Suppress("unused")
fun apiV1DetailRequestSerialize(obj: IDetailRequest): String =
    apiV1Mapper.encodeToString(IDetailRequest.serializer(), obj)

@Suppress("unused")
inline fun <reified T : IDetailRequest> apiV1RequestSimpleSerialize(obj: T): String =
    apiV1Mapper.encodeToString<T>(obj)

