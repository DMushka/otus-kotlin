package com.otus.otuskotlin.groschenberry.e2e.be.test.action.cib

import com.otus.otuskotlin.groschenberry.api.v1.models.*
import com.otus.otuskotlin.groschenberry.e2e.be.test.TestDebug

val debugStubCIB = CIBDebug(mode = CIRequestDebugMode.STUB, stub = CIBRequestDebugStubs.SUCCESS)

val someCreateCIB  = CIBCreateObject(
    title = "cib title",
    description = "cib description",
    country = Country.AUSTRALIA,
    currency = Currency.RUB,
    nominal = Nominal._1,
    material = "медь",
    diameter = 1.0,
    startYear = "2019",
    stopYear = "2019"
)

fun TestDebug.toV1() = when(this) {
    TestDebug.STUB -> debugStubCIB
    TestDebug.PROD -> CIBDebug(mode = CIRequestDebugMode.PROD)
    TestDebug.TEST -> CIBDebug(mode = CIRequestDebugMode.TEST)
}
