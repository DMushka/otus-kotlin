package com.otus.otuskotlin.groschenberry.mappers.v1

import com.otus.otuskotlin.groschenberry.api.v1.models.*
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCILock
import com.otus.otuskotlin.groschenberry.common.models.GrschbrWorkMode
import com.otus.otuskotlin.groschenberry.common.stubs.GrschbrStubs
import com.otus.otuskotlin.groschenberry.mappers.v1.exceptions.UnknownRequestClass

fun GrschbrContext.fromTransport(request: IBasicRequest) = when (request) {
    is CIBCreateRequest -> fromTransport(request)
    is CIBReadRequest -> fromTransport(request)
    is CIBUpdateRequest -> fromTransport(request)
    is CIBDeleteRequest -> fromTransport(request)
    is CIBSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toCIBId() = this?.let { GrschbrCIBId(it) } ?: GrschbrCIBId.NONE
private fun String?.toCIBLock() = this?.let { GrschbrCILock(it) } ?: GrschbrCILock.NONE

private fun CIBDebug?.transportToWorkMode(): GrschbrWorkMode = when (this?.mode) {
    CIRequestDebugMode.PROD -> GrschbrWorkMode.PROD
    CIRequestDebugMode.TEST -> GrschbrWorkMode.TEST
    CIRequestDebugMode.STUB -> GrschbrWorkMode.STUB
    null -> GrschbrWorkMode.PROD
}

private fun CIBDebug?.transportToStubCase(): GrschbrStubs = when (this?.stub) {
    CIBRequestDebugStubs.SUCCESS -> GrschbrStubs.SUCCESS
    CIBRequestDebugStubs.NOT_FOUND -> GrschbrStubs.NOT_FOUND
    CIBRequestDebugStubs.BAD_ID -> GrschbrStubs.BAD_ID
    CIBRequestDebugStubs.BAD_TITLE -> GrschbrStubs.BAD_TITLE
    CIBRequestDebugStubs.BAD_DESCRIPTION -> GrschbrStubs.BAD_DESCRIPTION
    CIBRequestDebugStubs.CANNOT_DELETE -> GrschbrStubs.CANNOT_DELETE
    CIBRequestDebugStubs.BAD_SEARCH_STRING -> GrschbrStubs.BAD_SEARCH_STRING
    CIBRequestDebugStubs.BAD_COUNTRY -> GrschbrStubs.BAD_COUNTRY
    CIBRequestDebugStubs.BAD_CURRENCY -> GrschbrStubs.BAD_CURRENCY
    CIBRequestDebugStubs.BAD_NOMINAL -> GrschbrStubs.BAD_NOMINAL
    CIBRequestDebugStubs.BAD_MATERIAL -> GrschbrStubs.BAD_MATERIAL
    CIBRequestDebugStubs.BAD_DIAMETER -> GrschbrStubs.BAD_DIAMETER
    CIBRequestDebugStubs.BAD_START_YEAR -> GrschbrStubs.BAD_START_YEAR
    CIBRequestDebugStubs.BAD_STOP_YEAR -> GrschbrStubs.BAD_STOP_YEAR
    null -> GrschbrStubs.NONE
}

fun GrschbrContext.fromTransport(request: CIBCreateRequest) {
    command = GrschbrCommand.CREATE
    cibRequest = request.cib?.toInternal() ?: GrschbrCIB()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun GrschbrContext.fromTransport(request: CIBReadRequest) {
    command = GrschbrCommand.READ
    cibRequest = request.cib.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun CIBReadObject?.toInternal(): GrschbrCIB = if (this != null) {
    GrschbrCIB(id = id.toCIBId())
} else {
    GrschbrCIB()
}

fun GrschbrContext.fromTransport(request: CIBUpdateRequest) {
    command = GrschbrCommand.UPDATE
    cibRequest = request.cib?.toInternal() ?: GrschbrCIB()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun GrschbrContext.fromTransport(request: CIBDeleteRequest) {
    command = GrschbrCommand.DELETE
    cibRequest = request.cib.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun CIBDeleteObject?.toInternal(): GrschbrCIB = if (this != null) {
    GrschbrCIB(
        id = id.toCIBId(),
        lock = lock.toCIBLock(),
    )
} else {
    GrschbrCIB()
}

fun GrschbrContext.fromTransport(request: CIBSearchRequest) {
    command = GrschbrCommand.SEARCH
    cibFilterRequest = request.cibFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun CIBSearchFilter?.toInternal(): GrschbrCIFilter = GrschbrCIFilter(
    searchString = this?.searchString ?: ""
)

private fun CIBCreateObject.toInternal(): GrschbrCIB = GrschbrCIB(
    title = this.title ?: "",
    description = this.description ?: "",
    country = this.country.fromTransport(),
    currency = this.currency.fromTransport(),
    nominal = this.nominal.fromTransport(),
    material = this.material ?: "",
    diameter = this.diameter ?: 0.0,
    startYear = this.startYear ?: "0000",
    stopYear = this.stopYear ?: "0000"
)

private fun CIBUpdateObject.toInternal(): GrschbrCIB = GrschbrCIB(
    id = this.id.toCIBId(),
    title = this.title ?: "",
    description = this.description ?: "",
    country = this.country.fromTransport(),
    currency = this.currency.fromTransport(),
    nominal = this.nominal.fromTransport(),
    material = this.material ?: "",
    diameter = this.diameter ?: 0.0,
    startYear = this.startYear ?: "0000",
    stopYear = this.stopYear ?: "0000",
    lock = lock.toCIBLock(),
)

private fun Country?.fromTransport(): GrschbrCountry = when (this) {
    Country.AUSTRALIA -> GrschbrCountry.AUSTRALIA
    Country.BELARUS -> GrschbrCountry.BELARUS
    Country.GREAT_BRITAIN -> GrschbrCountry.GREAT_BRITAIN
    Country.RUSSIA -> GrschbrCountry.RUSSIA
    null -> GrschbrCountry.NONE
}

private fun Currency?.fromTransport(): GrschbrCurrency = when (this) {
    Currency.RUB -> GrschbrCurrency.RUB
    Currency.EUR -> GrschbrCurrency.EUR
    Currency.USD -> GrschbrCurrency.USD
    Currency.CNY -> GrschbrCurrency.CNY
    null -> GrschbrCurrency.NONE
}

private fun Nominal?.fromTransport(): GrschbrNominal = when (this) {
    Nominal._1 -> GrschbrNominal._1
    Nominal._2 -> GrschbrNominal._2
    Nominal._5 -> GrschbrNominal._5
    Nominal._10 -> GrschbrNominal._10
    Nominal._15 -> GrschbrNominal._15
    Nominal._20 -> GrschbrNominal._20
    Nominal._25 -> GrschbrNominal._25
    Nominal._50 -> GrschbrNominal._50
    Nominal._100 -> GrschbrNominal._100
    null -> GrschbrNominal.NONE
}

