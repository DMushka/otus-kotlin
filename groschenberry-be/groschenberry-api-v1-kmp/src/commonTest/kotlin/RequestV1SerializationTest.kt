package com.otus.otuskotlin.groschenberry.api.v1

import com.otus.otuskotlin.groschenberry.api.v1.models.CIBCreateObject
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBCreateRequest
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBDebug
import com.otus.otuskotlin.groschenberry.api.v1.models.CIRequestDebugMode
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBRequestDebugStubs
import com.otus.otuskotlin.groschenberry.api.v1.models.Country
import com.otus.otuskotlin.groschenberry.api.v1.models.Currency
import com.otus.otuskotlin.groschenberry.api.v1.models.IBasicRequest
import com.otus.otuskotlin.groschenberry.api.v1.models.Nominal
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestV1SerializationTest {
    private val request: IBasicRequest = CIBCreateRequest(
        debug = CIBDebug (
            mode = CIRequestDebugMode.STUB,
            stub = CIBRequestDebugStubs.BAD_TITLE
        ),
        cib = CIBCreateObject(
            title = "cib title",
            description = "cib description",
            country = Country.AUSTRALIA,
            currency = Currency.RUB,
            nominal = Nominal._1,
            material = "медь",
            diameter = 1.0,
            //startYear = "2019-08-24",
            startYear = "2019",
            stopYear = "2019"
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.encodeToString(IBasicRequest.serializer(), request)

        println(json)

        assertContains(json, Regex("\"title\":\\s*\"cib title\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.encodeToString(request)
        val obj = apiV1Mapper.decodeFromString<IBasicRequest>(json) as CIBCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"cib": null}
        """.trimIndent()
        val obj = apiV1Mapper.decodeFromString<CIBCreateRequest>(jsonString)

        assertEquals(null, obj.cib)
    }
}
