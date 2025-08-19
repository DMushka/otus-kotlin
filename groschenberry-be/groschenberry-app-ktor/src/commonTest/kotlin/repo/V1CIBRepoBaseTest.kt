package com.otus.otuskotlin.groschenberry.app.ktor.repo

import com.otus.otuskotlin.groschenberry.api.v1.apiV1Mapper
import com.otus.otuskotlin.groschenberry.api.v1.mappers.toTransportCreateCIB
import com.otus.otuskotlin.groschenberry.api.v1.mappers.toTransportDeleteCIB
import com.otus.otuskotlin.groschenberry.api.v1.mappers.toTransportReadCIB
import com.otus.otuskotlin.groschenberry.api.v1.mappers.toTransportUpdateCIB
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBCreateRequest
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBCreateResponse
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBDebug
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBDeleteRequest
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBDeleteResponse
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBReadRequest
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBReadResponse
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBSearchRequest
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBSearchResponse
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBUpdateRequest
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBUpdateResponse
import com.otus.otuskotlin.groschenberry.api.v1.models.CISearchFilter
import com.otus.otuskotlin.groschenberry.api.v1.models.IBasicRequest
import com.otus.otuskotlin.groschenberry.app.ktor.GrschbrAppSettings
import com.otus.otuskotlin.groschenberry.app.ktor.module
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCILock
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

abstract class V1CIBRepoBaseTest : V1CIRepoBaseTest() {

    protected val initCIB = GrschbrCIBStub.prepareResult {
        id = GrschbrCIId(uuidOld)
        lock = GrschbrCILock(uuidOld)
    }

    @Test
    fun createCIB() {
        val cib = initCIB.toTransportCreateCIB()
        v1CIBTestApplication(
            conf = appSettingsCreate,
            func = "create",
            request = CIBCreateRequest(
                cib = cib,
                debug = CIBDebug(mode = workMode),
            ),
        ) { response ->
            val responseObj = response.body<CIBCreateResponse>()
            assertEquals(200, response.status.value)
            assertEquals(uuidNew, responseObj.cib?.id)
            assertEquals(cib.title, responseObj.cib?.title)
            assertEquals(cib.description, responseObj.cib?.description)
        }
    }

    @Test
    fun readCIB() {
        val cib = initCIB.toTransportReadCIB()
        v1CIBTestApplication(
            conf = appSettingsRead,
            func = "read",
            request = CIBReadRequest(
                cib = cib,
                debug = CIBDebug(mode = workMode),
            ),
        ) { response ->
            val responseObj = response.body<CIBReadResponse>()
            assertEquals(200, response.status.value)
            assertEquals(uuidOld, responseObj.cib?.id)
        }
    }

    @Test
    fun updateCIB() {
        val cib = initCIB.toTransportUpdateCIB()
        v1CIBTestApplication(
            conf = appSettingsUpdate,
            func = "update",
            request = CIBUpdateRequest(
                cib = cib,
                debug = CIBDebug(mode = workMode),
            ),
        ) { response ->
            val responseObj = response.body<CIBUpdateResponse>()
            assertEquals(200, response.status.value)
            assertEquals(cib.id, responseObj.cib?.id)
            assertEquals(cib.title, responseObj.cib?.title)
            assertEquals(cib.description, responseObj.cib?.description)
            assertEquals(uuidNew, responseObj.cib?.lock)
        }
    }
    @Test
    fun deleteCIB() {
        val cib = initCIB.toTransportDeleteCIB()
        v1CIBTestApplication(
            conf = appSettingsDelete,
            func = "delete",
            request = CIBDeleteRequest(
                cib = cib,
                debug = CIBDebug(mode = workMode),
            ),
        ) { response ->
            val responseObj = response.body<CIBDeleteResponse>()
            assertEquals(200, response.status.value)
            assertEquals(uuidOld, responseObj.cib?.id)
        }
    }

    @Test
    fun searchCIB() = v1CIBTestApplication(
        conf = appSettingsSearch,
        func = "search",
        request = CIBSearchRequest(
            ciFilter = CISearchFilter(),
            debug = CIBDebug(mode = workMode),
        ),
    ) { response ->
        val responseObj = response.body<CIBSearchResponse>()
        assertEquals(200, response.status.value)
        assertNotEquals(0, responseObj.cibs?.size)
        assertEquals(uuidOld, responseObj.cibs?.first()?.id)
    }


    private inline fun <reified T: IBasicRequest> v1CIBTestApplication(
        conf: GrschbrAppSettings,
        func: String,
        request: T,
        crossinline function: suspend (HttpResponse) -> Unit,
    ): Unit = testApplication {
        application { module(appSettings = conf) }
        val client = createClient {
            install(ContentNegotiation.Plugin) {
                json(apiV1Mapper)
            }
        }
        val response = client.post("/ci/basic/$func") {
            contentType(ContentType.Application.Json)
            header("X-Trace-Id", "12345")
            setBody(request)
        }
        function(response)
    }

}