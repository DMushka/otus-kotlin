package com.otus.otuskotlin.groschenberry.mappers.v1

import com.otus.otuskotlin.groschenberry.api.v1.models.*
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.exceptions.UnknownGrschbrCommand
import com.otus.otuskotlin.groschenberry.common.models.*

fun GrschbrContext.toTransportCIB(): IBasicResponse = when (val cmd = command) {
    GrschbrCommand.CREATE -> toTransportCreate()
    GrschbrCommand.READ -> toTransportRead()
    GrschbrCommand.UPDATE -> toTransportUpdate()
    GrschbrCommand.DELETE -> toTransportDelete()
    GrschbrCommand.SEARCH -> toTransportSearch()
    GrschbrCommand.NONE -> throw UnknownGrschbrCommand(cmd)
}

fun GrschbrContext.toTransportCreate() = CIBCreateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    cib = cibResponse.toTransportCIB()
)

fun GrschbrContext.toTransportRead() = CIBReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    cib = cibResponse.toTransportCIB()
)

fun GrschbrContext.toTransportUpdate() = CIBUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    cib = cibResponse.toTransportCIB()
)

fun GrschbrContext.toTransportDelete() = CIBDeleteResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    cib = cibResponse.toTransportCIB()
)

fun GrschbrContext.toTransportSearch() = CIBSearchResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    cibs = cibsResponse.toTransportCIB()
)

fun List<GrschbrCIB>.toTransportCIB(): List<CIBResponseObject>? = this
    .map { it.toTransportCIB() }
    .toList()
    .takeIf { it.isNotEmpty() }

fun GrschbrCIB.toTransportCIB(): CIBResponseObject = CIBResponseObject(
    id = id.toTransportCIB(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    country = this.country.toTransport(),
    currency = this.currency.toTransport(),
    nominal = this.nominal.toTransportCIB(),
    material = this.material.takeIf { it.isNotBlank() },
    diameter = this.diameter.takeIf { it != 0.0 },
    startYear = this.startYear.takeIf { it != "0000"},
    stopYear = this.stopYear.takeIf { it != "0000"},
    permissions = permissionsClient.toTransportCIB(),
)

internal fun GrschbrCIBId.toTransportCIB() = takeIf { it != GrschbrCIBId.NONE }?.asString()

private fun Set<GrschbrCIPermissionClient>.toTransportCIB(): Set<CIPermissions>? = this
    .map { it.toTransportCI() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun GrschbrCIPermissionClient.toTransportCI() = when (this) {
    GrschbrCIPermissionClient.READ -> CIPermissions.READ
    GrschbrCIPermissionClient.UPDATE -> CIPermissions.UPDATE
    GrschbrCIPermissionClient.DELETE -> CIPermissions.DELETE
}

private fun List<GrschbrError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportCI() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun GrschbrError.toTransportCI() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

internal fun GrschbrCountry.toTransport(): Country? = when (this) {
    GrschbrCountry.AUSTRALIA -> Country.AUSTRALIA
    GrschbrCountry.BELARUS -> Country.BELARUS
    GrschbrCountry.GREAT_BRITAIN -> Country.GREAT_BRITAIN
    GrschbrCountry.RUSSIA -> Country.RUSSIA
    GrschbrCountry.NONE -> null
}

internal fun GrschbrCurrency.toTransport(): Currency? = when (this) {
    GrschbrCurrency.RUB -> Currency.RUB
    GrschbrCurrency.EUR -> Currency.EUR
    GrschbrCurrency.USD -> Currency.USD
    GrschbrCurrency.CNY -> Currency.CNY
    GrschbrCurrency.NONE -> null
}

internal fun GrschbrNominal.toTransportCIB(): Nominal? = when (this) {
    GrschbrNominal._1 -> Nominal._1
    GrschbrNominal._2 -> Nominal._2
    GrschbrNominal._5 -> Nominal._5
    GrschbrNominal._10 -> Nominal._10
    GrschbrNominal._15 -> Nominal._15
    GrschbrNominal._20 -> Nominal._20
    GrschbrNominal._25 -> Nominal._25
    GrschbrNominal._50 -> Nominal._50
    GrschbrNominal._100 -> Nominal._100
    GrschbrNominal.NONE -> null
}

private fun GrschbrState.toResult(): ResponseResult? = when (this) {
    GrschbrState.RUNNING -> ResponseResult.SUCCESS
    GrschbrState.FAILING -> ResponseResult.ERROR
    GrschbrState.FINISHING -> ResponseResult.SUCCESS
    GrschbrState.NONE -> null
}
