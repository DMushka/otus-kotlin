package com.otus.otuskotlin.groschenberry.app.ktor.stub

import com.otus.otuskotlin.groschenberry.api.v1.apiV1Mapper
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import com.otus.otuskotlin.groschenberry.api.v1.models.*
import com.otus.otuskotlin.groschenberry.app.ktor.GrschbrAppSettings
import com.otus.otuskotlin.groschenberry.app.ktor.module
import com.otus.otuskotlin.groschenberry.common.GrschbrCorSettings
import kotlin.test.Test
import kotlin.test.assertEquals

class V1CIBStubApiTest {

    @Test
    fun create() = v1CIBTestApplication(
        func = "create",
        request = CIBCreateRequest(
            cib = CIBCreateObject(
                title = "Монета 1",
                description = "Монета стандартная круглая",
                country = Country.RUSSIA,
                currency = Currency.RUB,
                nominal = Nominal._1,
                material = "Медь",
                diameter = 5.0,
                startYear = "1956",
                stopYear = "1961"
            ),
            debug = CIBDebug(
                mode = CIRequestDebugMode.STUB,
                stub = CIBRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<CIBCreateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.cib?.id)
    }

    @Test
    fun read() = v1CIBTestApplication(
        func = "read",
        request = CIBReadRequest(
            cib = CIBReadObject("666"),
            debug = CIBDebug(
                mode = CIRequestDebugMode.STUB,
                stub = CIBRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<CIBReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.cib?.id)
    }

    @Test
    fun update() = v1CIBTestApplication(
        func = "update",
        request = CIBUpdateRequest(
            cib = CIBUpdateObject(
                id = "666",
                title = "Монета 1",
                description = "Монета стандартная круглая",
                country = Country.RUSSIA,
                currency = Currency.RUB,
                nominal = Nominal._1,
                material = "Медь",
                diameter = 5.0,
                startYear = "1956",
                stopYear = "1961"
            ),
            debug = CIBDebug(
                mode = CIRequestDebugMode.STUB,
                stub = CIBRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<CIBUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.cib?.id)
    }

    @Test
    fun delete() = v1CIBTestApplication(
        func = "delete",
        request = CIBDeleteRequest(
            cib = CIBDeleteObject(
                id = "666",
                lock = "123"
            ),
            debug = CIBDebug(
                mode = CIRequestDebugMode.STUB,
                stub = CIBRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<CIBDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals("666", responseObj.cib?.id)
    }

    @Test
    fun search() = v1CIBTestApplication(
        func = "search",
        request = CIBSearchRequest(
            ciFilter = CISearchFilter(),
            debug = CIBDebug(
                mode = CIRequestDebugMode.STUB,
                stub = CIBRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<CIBSearchResponse>()
        assertEquals(200, response.status.value)
        assertEquals("d-666-01", responseObj.cibs?.first()?.id)
    }

    private inline fun <reified T: IBasicRequest> v1CIBTestApplication(
        func: String,
        request: T,
        crossinline function: suspend (HttpResponse) -> Unit,
    ): Unit = testApplication {
        application { module(GrschbrAppSettings(corSettings = GrschbrCorSettings())) }
        val client = createClient {
            install(ContentNegotiation) {
                json(apiV1Mapper)
            }
        }
        val response = client.post("/ci/basic/$func") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        function(response)
    }
}
