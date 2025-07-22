package com.otus.otuskotlin.groschenberry.app.ktor.repo

import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import com.otus.otuskotlin.groschenberry.api.v1.apiV1Mapper
import com.otus.otuskotlin.groschenberry.api.v1.models.*
import com.otus.otuskotlin.groschenberry.app.ktor.GrschbrAppSettings
import com.otus.otuskotlin.groschenberry.app.ktor.module
import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

abstract class V1CIRepoBaseTest {
    abstract val workMode: CIRequestDebugMode
    abstract val appSettingsCreate: GrschbrAppSettings
    abstract val appSettingsRead:   GrschbrAppSettings
    abstract val appSettingsUpdate: GrschbrAppSettings
    abstract val appSettingsDelete: GrschbrAppSettings
    abstract val appSettingsSearch: GrschbrAppSettings

    protected val uuidOld = "10000000-0000-0000-0000-000000000001"
    protected val uuidNew = "10000000-0000-0000-0000-000000000002"
    protected val initCIB = GrschbrCIBStub.prepareResult {
        id = GrschbrCIId(uuidOld)
        lock = GrschbrCILock(uuidOld)
    }

/*    @Test
    fun search() = v1TestApplication(
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
    }*/

    private inline fun <reified T: IBasicRequest> v1TestApplication(
        conf: GrschbrAppSettings,
        func: String,
        request: T,
        crossinline function: suspend (HttpResponse) -> Unit,
    ): Unit = testApplication {
        application { module(appSettings = conf) }
        val client = createClient {
            install(ContentNegotiation) {
                json(apiV1Mapper)
            }
        }
        val response = client.post("/v1/cib/$func") {
            contentType(ContentType.Application.Json)
            header("X-Trace-Id", "12345")
            setBody(request)
        }
        function(response)
    }
}
