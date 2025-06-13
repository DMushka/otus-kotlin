package com.otus.otuskotlin.groschenberry.api.v1

import com.otus.otuskotlin.groschenberry.api.v1.models.CIBCreateResponse
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBResponseObject
import com.otus.otuskotlin.groschenberry.api.v1.models.Country
import com.otus.otuskotlin.groschenberry.api.v1.models.Currency
import com.otus.otuskotlin.groschenberry.api.v1.models.IBasicResponse
import com.otus.otuskotlin.groschenberry.api.v1.models.Nominal
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseV1SerializationTest {
    private val response: IBasicResponse = CIBCreateResponse(
        cib = CIBResponseObject(
            title = "cib title",
            description = "cib description",
            country = Country.АВСТРАЛИЯ,
            currency = Currency.RUS,
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
//        val json = apiV2Mapper.encodeToString(CIBRequestSerializer1, request)
//        val json = apiV2Mapper.encodeToString(RequestSerializers.create, request)
        val json = apiV1Mapper.encodeToString(response)

        println(json)

        assertContains(json, Regex("\"title\":\\s*\"cib title\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.encodeToString(response)
        val obj = apiV1Mapper.decodeFromString<IBasicResponse>(json) as CIBCreateResponse

        assertEquals(response, obj)
    }
}
