package com.otus.otuskotlin.groschenberry.e2e.be.test.action.cib

import co.touchlab.kermit.Logger
import com.otus.otuskotlin.groschenberry.api.v1.apiV1BasicRequestSerialize
import com.otus.otuskotlin.groschenberry.api.v1.apiV1BasicResponseDeserialize
import com.otus.otuskotlin.groschenberry.api.v1.models.IBasicRequest
import com.otus.otuskotlin.groschenberry.api.v1.models.IBasicResponse
import com.otus.otuskotlin.groschenberry.e2e.be.fixture.client.Client

private val log = Logger

suspend fun Client.sendAndReceive(path: String, request: IBasicRequest): IBasicResponse {
    val requestBody = apiV1BasicRequestSerialize(request)
    log.i { "Send to ci/basic/$path\n$requestBody" }

    val responseBody = sendAndReceive("basic", path, requestBody)
    log.i { "Received\n$responseBody" }

    return apiV1BasicResponseDeserialize(responseBody)
}
