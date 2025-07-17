package com.otus.otuskotlin.groschenberry.app.common

import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.groschenberry.api.v1.mappers.*
import com.otus.otuskotlin.groschenberry.api.v1.models.*
import com.otus.otuskotlin.groschenberry.biz.GrschbrCIProcessor
import com.otus.otuskotlin.groschenberry.common.GrschbrCorSettings
import kotlin.test.Test
import kotlin.test.assertEquals

class ControllerV1Test {

    private val request = CIBCreateRequest(
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
        debug = CIBDebug(mode = CIRequestDebugMode.STUB, stub = CIBRequestDebugStubs.SUCCESS)
    )

    private val appSettings: IGrschbrAppSettings = object : IGrschbrAppSettings {
        override val corSettings: GrschbrCorSettings = GrschbrCorSettings()
        override val processor: GrschbrCIProcessor = GrschbrCIProcessor(corSettings)
    }

    class TestApplicationCall(private val request: IBasicRequest) {
        var res: IBasicResponse? = null

        @Suppress("UNCHECKED_CAST")
        fun <T : IBasicRequest> receive(): T = request as T
        fun respond(res: IBasicResponse) {
            this.res = res
        }
    }

    private suspend fun TestApplicationCall.createCIBKtor(appSettings: IGrschbrAppSettings) {
        val resp = appSettings.controllerHelper(
            { fromTransport(receive<CIBCreateRequest>()) },
            { toTransportCIB() },
            ControllerV1Test::class,
            "controller-v1-test"
        )
        respond(resp)
    }

    @Test
    fun ktorHelperTest() = runTest {
        val testApp = TestApplicationCall(request).apply { createCIBKtor(appSettings) }
        val res = testApp.res as CIBCreateResponse
        assertEquals(ResponseResult.SUCCESS, res.result)
    }
}
