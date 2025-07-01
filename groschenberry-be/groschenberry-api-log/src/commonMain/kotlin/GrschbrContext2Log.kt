package com.otus.otuskotlin.groschenberry.api.log.mapper

import kotlinx.datetime.Clock
import com.otus.otuskotlin.groschenberry.api.log.models.*
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.*
import kotlin.takeIf

fun GrschbrContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "groschenberry",
    ci = toGrschbrLog(),
    errors = errors.map { it.toLog() },
)

private fun GrschbrContext.toGrschbrLog(): GrschbrCILogModel? {
    val cibNone = GrschbrCIB()
    val cidNone = GrschbrCID()
    return GrschbrCILogModel(
        ciType = if (cidRequest != cidNone) CIType.DETAIL else if (cibRequest != cibNone) CIType.BASIC else CIType.OTHER,
        requestId = requestId.takeIf { it != GrschbrRequestId.NONE }?.asString(),
        requestCI = cidRequest.takeIf { it != cidNone }?.toLog() ?: cibRequest.takeIf { it != cibNone }?.toLog(),
        responseCI = cidResponse.takeIf { it != cidNone }?.toLog() ?: cibResponse.takeIf { it != cibNone }?.toLog(),
        responseCIs = cidsResponse.takeIf { it.isNotEmpty() }?.filter { it != cidNone }?.map { it.toLog() } ?: cibsResponse.takeIf { it.isNotEmpty() }?.filter { it != cibNone }?.map { it.toLog() },
        requestFilter = ciFilterRequest.takeIf { it != GrschbrCIFilter() }?.toLog(),
    ).takeIf { it != GrschbrCILogModel() }
}

private fun GrschbrCIFilter.toLog() = CIFilterLog(
    searchString = searchString.takeIf { it.isNotBlank() },
    )

private fun GrschbrError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)

private fun GrschbrCIB.toLog() = CIBLog(
    id = id.toLogId(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    country = country.takeIf { it != GrschbrCountry.NONE }?.name,
    currency = currency.takeIf { it != GrschbrCurrency.NONE }?.name,
    nominal = nominal.takeIf { it != GrschbrNominal.NONE }?.value.toString(),
    material = this.material.takeIf { it.isNotBlank()},
    diameter = this.diameter.takeIf { it != 0.0 },
    startYear = this.startYear.takeIf { it != "0000" },
    stopYear = this.startYear.takeIf { it != "0000" },
    permissions = permissionsClient.toLog(),
).toLog()

fun GrschbrCID.toLog() = CIDLog(
    id = id.toLogId(),
    description = description.takeIf { it.isNotBlank() },
    mint = mint.takeIf { it.isNotBlank() },
    copies = copies.takeIf { it != 0 },
    issueYear = issueYear.takeIf { it != "0000"},
    permissions = permissionsClient.toLog(),
    cibId = cibId.toLogId(),
).toLog()

internal fun GrschbrCIId.toLogId() = takeIf { it != GrschbrCIId.NONE }?.asString()
internal fun  MutableSet<GrschbrCIPermissionClient>.toLog()  = takeIf { it.isNotEmpty() }?.map { it.name }?.toSet()

private fun CIBLog.toLog() = GrschbrCILogModelRequestCI(
    id = id,
    title = title,
    description = description,
    country = country,
    currency = currency,
    nominal = nominal,
    material = material,
    diameter = diameter,
    startYear = startYear,
    stopYear = startYear,
    permissions = permissions
)

private fun CIDLog.toLog() = GrschbrCILogModelRequestCI(
    id = id,
    description = description,
    mint = mint,
    copies = copies,
    issueYear = issueYear,
    permissions = permissions,
    cibId = cibId,
)