package com.otus.otuskotlin.groschenberry.app.ktor.auth

import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import com.otus.otuskotlin.groschenberry.api.v1.apiV1Mapper
import com.otus.otuskotlin.groschenberry.api.v1.models.*
import com.otus.otuskotlin.groschenberry.app.ktor.GrschbrAppSettings
import com.otus.otuskotlin.groschenberry.app.ktor.module
import com.otus.otuskotlin.groschenberry.common.GrschbrCorSettings
import com.otus.otuskotlin.groschenberry.repo.inmemory.CIRepoInMemory
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthTest {
    @Test
    fun invalidAudience() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json(apiV1Mapper)
            }
        }
        application { module(GrschbrAppSettings(corSettings = GrschbrCorSettings(repoTest = CIRepoInMemory()))) }
        val response = client.post("/ci/basic/create") {
            addAuth(groups = emptyList())
            contentType(ContentType.Application.Json)
            setBody(
                CIBCreateRequest(
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
                    debug = CIBDebug(mode = CIRequestDebugMode.TEST)
                )
            )
        }
        val cibObj = response.body<CIBCreateResponse>()
        assertEquals(200, response.status.value)
        assertEquals(ResponseResult.ERROR, cibObj.result)
        assertEquals("access-create", cibObj.errors?.first()?.code)
    }
}
