package com.otus.otuskotlin.groschenberry.app.ktor.repo

import com.otus.otuskotlin.groschenberry.api.v1.apiV1Mapper
import com.otus.otuskotlin.groschenberry.api.v1.mappers.toTransportCreateCID
import com.otus.otuskotlin.groschenberry.api.v1.mappers.toTransportDeleteCID
import com.otus.otuskotlin.groschenberry.api.v1.mappers.toTransportReadCID
import com.otus.otuskotlin.groschenberry.api.v1.mappers.toTransportUpdateCID
import com.otus.otuskotlin.groschenberry.api.v1.models.CIDCreateRequest
import com.otus.otuskotlin.groschenberry.api.v1.models.CIDCreateResponse
import com.otus.otuskotlin.groschenberry.api.v1.models.CIDDebug
import com.otus.otuskotlin.groschenberry.api.v1.models.CIDDeleteRequest
import com.otus.otuskotlin.groschenberry.api.v1.models.CIDDeleteResponse
import com.otus.otuskotlin.groschenberry.api.v1.models.CIDReadRequest
import com.otus.otuskotlin.groschenberry.api.v1.models.CIDReadResponse
import com.otus.otuskotlin.groschenberry.api.v1.models.CIDSearchRequest
import com.otus.otuskotlin.groschenberry.api.v1.models.CIDSearchResponse
import com.otus.otuskotlin.groschenberry.api.v1.models.CIDUpdateRequest
import com.otus.otuskotlin.groschenberry.api.v1.models.CIDUpdateResponse
import com.otus.otuskotlin.groschenberry.api.v1.models.CISearchFilter
import com.otus.otuskotlin.groschenberry.api.v1.models.IDetailRequest
import com.otus.otuskotlin.groschenberry.app.ktor.GrschbrAppSettings
import com.otus.otuskotlin.groschenberry.app.ktor.auth.addAuth
import com.otus.otuskotlin.groschenberry.app.ktor.module
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCILock
import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrUserGroups
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub
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

abstract class V1CIDRepoBaseTest: V1CIRepoBaseTest() {
    protected val initCID = GrschbrCIDStub.prepareResult {
        id = GrschbrCIId(uuidOld)
        lock = GrschbrCILock(uuidOld)
        cibId = GrschbrCIId(uuidOld)
    }

    @Test
    fun createCID() {
        val cid = initCID.toTransportCreateCID()
        v1CIDTestApplication(
            conf = appSettingsCreate,
            func = "create",
            request = CIDCreateRequest(
                cid = cid,
                debug = CIDDebug(mode = workMode),
            ),
        ) { response ->
            val responseObj = response.body<CIDCreateResponse>()
            assertEquals(200, response.status.value)
            assertEquals(uuidNew, responseObj.cid?.id)
            assertEquals(cid.description, responseObj.cid?.description)
        }
    }

    @Test
    fun readCID() {
        val cid = initCID.toTransportReadCID()
        v1CIDTestApplication(
            conf = appSettingsRead,
            func = "read",
            request = CIDReadRequest(
                cid = cid,
                debug = CIDDebug(mode = workMode),
            ),
        ) { response ->
            val responseObj = response.body<CIDReadResponse>()
            assertEquals(200, response.status.value)
            assertEquals(uuidOld, responseObj.cid?.id)
        }
    }

    @Test
    fun updateCID() {
        val cid = initCID.toTransportUpdateCID()
        v1CIDTestApplication(
            conf = appSettingsUpdate,
            func = "update",
            request = CIDUpdateRequest(
                cid = cid,
                debug = CIDDebug(mode = workMode),
            ),
        ) { response ->
            val responseObj = response.body<CIDUpdateResponse>()
            assertEquals(200, response.status.value)
            assertEquals(cid.id, responseObj.cid?.id)
            assertEquals(cid.description, responseObj.cid?.description)
            assertEquals(uuidNew, responseObj.cid?.lock)
        }
    }
    @Test
    fun deleteCID() {
        val cid = initCID.toTransportDeleteCID()
        v1CIDTestApplication(
            conf = appSettingsDelete,
            func = "delete",
            request = CIDDeleteRequest(
                cid = cid,
                debug = CIDDebug(mode = workMode),
            ),
        ) { response ->
            val responseObj = response.body<CIDDeleteResponse>()
            assertEquals(200, response.status.value)
            assertEquals(uuidOld, responseObj.cid?.id)
        }
    }

    @Test
    fun searchCID() = v1CIDTestApplication(
        conf = appSettingsSearch,
        func = "search",
        request = CIDSearchRequest(
            ciFilter = CISearchFilter(),
            debug = CIDDebug(mode = workMode),
        ),
    ) { response ->
        val responseObj = response.body<CIDSearchResponse>()
        assertEquals(200, response.status.value)
        assertNotEquals(0, responseObj.cids?.size)
        assertEquals(uuidOld, responseObj.cids?.first()?.id)
    }

    private inline fun <reified T: IDetailRequest> v1CIDTestApplication(
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
        val response = client.post("/ci/detail/$func") {
            contentType(ContentType.Application.Json)
            header("X-Trace-Id", "12345")
            addAuth()
            setBody(request)
        }
        function(response)
    }
}