package com.otus.otuskotlin.groschenberry.api.v1.mappers

import com.otus.otuskotlin.groschenberry.api.v1.models.*
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.exceptions.UnknownGrschbrCommand
import com.otus.otuskotlin.groschenberry.common.models.*

fun GrschbrContext.toTransportCIB(): IBasicResponse = when (val cmd = command) {
    GrschbrCommand.CREATE -> toTransportCIBCreate()
    GrschbrCommand.READ -> toTransportCIBRead()
    GrschbrCommand.UPDATE -> toTransportCIBUpdate()
    GrschbrCommand.DELETE -> toTransportCIBDelete()
    GrschbrCommand.SEARCH -> toTransportCIBSearch()
    GrschbrCommand.NONE -> throw UnknownGrschbrCommand(cmd)
}

fun GrschbrContext.toTransportCID(): IDetailResponse = when (val cmd = command) {
    GrschbrCommand.CREATE -> toTransportCIDCreate()
    GrschbrCommand.READ -> toTransportCIDRead()
    GrschbrCommand.UPDATE -> toTransportCIDUpdate()
    GrschbrCommand.DELETE -> toTransportCIDDelete()
    GrschbrCommand.SEARCH -> toTransportCIDSearch()
    GrschbrCommand.NONE -> throw UnknownGrschbrCommand(cmd)
}

fun GrschbrContext.toTransportCIBCreate() = CIBCreateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    cib = cibResponse.toTransportCIB()
)

fun GrschbrContext.toTransportCIDCreate() = CIDCreateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    cid = cidResponse.toTransportCID()
)

fun GrschbrContext.toTransportCIBRead() = CIBReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    cib = cibResponse.toTransportCIB()
)

fun GrschbrContext.toTransportCIDRead() = CIDReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    cid = cidResponse.toTransportCID()
)

fun GrschbrContext.toTransportCIBUpdate() = CIBUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    cib = cibResponse.toTransportCIB()
)

fun GrschbrContext.toTransportCIDUpdate() = CIDUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    cid = cidResponse.toTransportCID()
)

fun GrschbrContext.toTransportCIBDelete() = CIBDeleteResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    cib = cibResponse.toTransportCIB()
)

fun GrschbrContext.toTransportCIDDelete() = CIDDeleteResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    cid = cidResponse.toTransportCID()
)

fun GrschbrContext.toTransportCIBSearch() = CIBSearchResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    cibs = cibsResponse.toTransportCIB()
)

fun GrschbrContext.toTransportCIDSearch() = CIDSearchResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    cids = cidsResponse.toTransportCID()
)

fun List<GrschbrCIB>.toTransportCIB(): List<CIBResponseObject>? = this
    .map { it.toTransportCIB() }
    .toList()
    .takeIf { it.isNotEmpty() }

fun List<GrschbrCID>.toTransportCID(): List<CIDResponseObject>? = this
    .map { it.toTransportCID() }
    .toList()
    .takeIf { it.isNotEmpty() }

fun GrschbrCIB.toTransportCIB(): CIBResponseObject = CIBResponseObject(
    id = id.toTransportCI(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    country = this.country.toTransportCIB(),
    currency = this.currency.toTransportCIB(),
    nominal = this.nominal.toTransportCIB(),
    material = this.material.takeIf { it.isNotBlank() },
    diameter = this.diameter.takeIf { it != 0.0 },
    startYear = this.startYear.takeIf { it != "0000"},
    stopYear = this.stopYear.takeIf { it != "0000"},
    permissions = permissionsClient.toTransportCIB(),
)

fun GrschbrCID.toTransportCID(): CIDResponseObject = CIDResponseObject(
    id = id.toTransportCI(),
    description = description.takeIf { it.isNotBlank() },
    mint = this.mint.takeIf { it.isNotBlank() },
    copies = this.copies.takeIf { it != 0 },
    issueYear = this.issueYear.takeIf { it != "0000"},
    permissions = permissionsClient.toTransportCIB(),
    cibId = this.cibId.toTransportCI(),
)

internal fun GrschbrCIId.toTransportCI() = takeIf { it != GrschbrCIId.NONE }?.asString()

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

internal fun GrschbrCountry.toTransportCIB(): Country? = when (this) {
    GrschbrCountry.AUSTRALIA -> Country.AUSTRALIA
    GrschbrCountry.BELARUS -> Country.BELARUS
    GrschbrCountry.GREAT_BRITAIN -> Country.GREAT_BRITAIN
    GrschbrCountry.RUSSIA -> Country.RUSSIA
    GrschbrCountry.NONE -> null
}

internal fun GrschbrCurrency.toTransportCIB(): Currency? = when (this) {
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
