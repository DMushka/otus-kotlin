package com.otus.otuskotlin.groschenberry.api.log.mapper

import kotlinx.datetime.Clock
import com.otus.otuskotlin.groschenberry.api.log.models.*
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrPrincipalModel
import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrUserGroups
import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrUserPermissions
import kotlin.takeIf

fun GrschbrContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "groschenberry",
    ci = toGrschbrLog(),
    errors = errors.map { it.toLog() },
)

private fun GrschbrContext.toGrschbrLog(): GrschbrCILogModel? {
    return GrschbrCILogModel(
        ciType = type.toLog(),
        requestId = requestId.takeIf { it != GrschbrRequestId.NONE }?.asString(),
        state = state.toLog(),
        principal = principal.toLog(),
        permissionsChain = permissionsChain.map { it.toLog() },
        permitted = permitted,
        requestCI = when(type) {
            GrschbrType.BASIC -> cibRequest.takeIf { !it.isEmpty() }?.toLog()
            GrschbrType.DETAIL -> cidRequest.takeIf { !it.isEmpty() }?.toLog()
            GrschbrType.NONE -> null
        },
        responseCI = when(type) {
            GrschbrType.BASIC -> cibResponse.takeIf { !it.isEmpty() }?.toLog()
            GrschbrType.DETAIL -> cidResponse.takeIf { !it.isEmpty() }?.toLog()
            GrschbrType.NONE -> null
        },
        validatedCI = when(type) {
            GrschbrType.BASIC -> cibValidated.takeIf { !it.isEmpty() }?.toLog()
            GrschbrType.DETAIL -> cidValidated.takeIf { !it.isEmpty() }?.toLog()
            GrschbrType.NONE -> null
        },
        repoReadCI = when(type) {
            GrschbrType.BASIC -> cibRepoRead.takeIf { !it.isEmpty() }?.toLog()
            GrschbrType.DETAIL -> cidRepoRead.takeIf { !it.isEmpty() }?.toLog()
            GrschbrType.NONE -> null
        },
        repoPrepareCI = when(type) {
            GrschbrType.BASIC -> cibRepoPrepare.takeIf { !it.isEmpty() }?.toLog()
            GrschbrType.DETAIL -> cidRepoPrepare.takeIf { !it.isEmpty() }?.toLog()
            GrschbrType.NONE -> null
        },
        repoDoneCI = when(type) {
            GrschbrType.BASIC -> cibRepoDone.takeIf { !it.isEmpty() }?.toLog()
            GrschbrType.DETAIL -> cidRepoDone.takeIf { !it.isEmpty() }?.toLog()
            GrschbrType.NONE -> null
        },
        responseCIs = when(type) {
            GrschbrType.BASIC -> cibsResponse.takeIf { it.isNotEmpty() }?.filter { !it.isEmpty() }?.map { it.toLog() }
            GrschbrType.DETAIL -> cidsResponse.takeIf { it.isNotEmpty() }?.filter { !it.isEmpty() }?.map { it.toLog() }
            GrschbrType.NONE -> null
        },
        filterRequest = ciFilterRequest.takeIf { !it.isEmpty() }?.toLog(),
        filterValidated = ciFilterValidated.takeIf { !it.isEmpty() }?.toLog(),

    ).takeIf { it != GrschbrCILogModel() }
}

private fun GrschbrCIFilter.toLog() = CIFilterLog(
    searchString = searchString.takeIf { it.isNotEmpty() },
    )

private fun GrschbrError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)

private fun GrschbrCIB.toLog() = CIBLog(
    id = id.toLog(),
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
    lock = lock.toLog(),
).toLog()

fun GrschbrCID.toLog() = CIDLog(
    id = id.toLog(),
    description = description.takeIf { it.isNotBlank() },
    mint = mint.takeIf { it.isNotBlank() },
    copies = copies.takeIf { it != 0 },
    issueYear = issueYear.takeIf { it != "0000"},
    permissions = permissionsClient.toLog(),
    cibId = cibId.toLog(),
    lock = lock.toLog(),
).toLog()

internal fun GrschbrCIId.toLog() = takeIf { it != GrschbrCIId.NONE }?.asString()
internal fun GrschbrUserId.toLog() = takeIf { it != GrschbrUserId.NONE }?.asString()
internal fun GrschbrCILock.toLog() = takeIf { it != GrschbrCILock.NONE }?.asString()
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
    stopYear = stopYear,
    permissions = permissions,
    lock = lock,
)

private fun CIDLog.toLog() = GrschbrCILogModelRequestCI(
    id = id,
    description = description,
    mint = mint,
    copies = copies,
    issueYear = issueYear,
    permissions = permissions,
    cibId = cibId,
    lock = lock,
)

private fun GrschbrPrincipalModel.toLog() = Principal(
    id = id.toLog(),
    name = fname.takeIf { it.length > 3 }?.substring(0,3) + "***" + lname.takeIf { it.length > 3 }?.takeLast(3),
    groups = groups.map { it.toLog() }
)

private fun GrschbrType.toLog() : CIType = when(this) {
    GrschbrType.NONE -> CIType.NONE
    GrschbrType.BASIC -> CIType.BASIC
    GrschbrType.DETAIL -> CIType.DETAIL
}

private fun GrschbrState.toLog() : CIState = when(this) {
    GrschbrState.NONE -> CIState.NONE
    GrschbrState.RUNNING -> CIState.RUNNING
    GrschbrState.FAILING -> CIState.FAILING
    GrschbrState.FINISHED -> CIState.FINISHED
}

private fun GrschbrUserPermissions.toLog() : Permissions = when(this) {
    GrschbrUserPermissions.SEARCH -> Permissions.SEARCH
    GrschbrUserPermissions.READ -> Permissions.READ
    GrschbrUserPermissions.CREATE -> Permissions.CREATE
    GrschbrUserPermissions.UPDATE -> Permissions.UPDATE
    GrschbrUserPermissions.DELETE -> Permissions.DELETE
}

private fun GrschbrUserGroups.toLog() : Groups = when(this) {
    GrschbrUserGroups.TEST -> Groups.TEST
    GrschbrUserGroups.ADMIN_CI -> Groups.ADMIN
    GrschbrUserGroups.EXPERT -> Groups.EXPERT
    GrschbrUserGroups.USER -> Groups.USER
}