package com.otus.otuskotlin.groschenberry.app.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import com.otus.otuskotlin.groschenberry.common.models.GrschbrUserId
import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrPrincipalModel
import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrUserGroups
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

const val AUTH_HEADER: String = "x-jwt-payload"

@OptIn(ExperimentalEncodingApi::class)
fun String?.jwt2principal(): GrschbrPrincipalModel = this?.let { jwtHeader ->
        val jwtJson = Base64.decode(jwtHeader).decodeToString()
        println("JWT JSON PAYLOAD: $jwtJson")
        val jwtObj = jsMapper.decodeFromString(JwtPayload.serializer(), jwtJson)
        jwtObj.toPrincipal()
    }
    ?: run {
        println("No jwt found in headers")
        GrschbrPrincipalModel.NONE
    }

@OptIn(ExperimentalEncodingApi::class)
fun GrschbrPrincipalModel.createJwtTestHeader(): String {
    val jwtObj = fromPrincipal()
    val jwtJson = jsMapper.encodeToString(JwtPayload.serializer(), jwtObj)
    return Base64.encode(jwtJson.encodeToByteArray())
}

private val jsMapper = Json {
    ignoreUnknownKeys = true
}

@Serializable
private data class JwtPayload(
    val aud: List<String>? = null,
    val sub: String? = null,
    @SerialName("family_name")
    val familyName: String? = null,
    @SerialName("given_name")
    val givenName: String? = null,
    @SerialName("middle_name")
    val middleName: String? = null,
    val groups: List<String>? = null,
)

private fun JwtPayload.toPrincipal(): GrschbrPrincipalModel = GrschbrPrincipalModel(
    id = sub?.let { GrschbrUserId(it) } ?: GrschbrUserId.NONE,
    fname = givenName ?: "",
    mname = middleName ?: "",
    lname = familyName ?: "",
    groups = groups?.mapNotNull { it.toPrincipalGroup() }?.toSet() ?: emptySet(),
)

private fun GrschbrPrincipalModel.fromPrincipal(): JwtPayload = JwtPayload(
    sub = id.takeIf { it != GrschbrUserId.NONE }?.asString(),
    givenName = fname.takeIf { it.isNotBlank() },
    middleName = mname.takeIf { it.isNotBlank() },
    familyName = lname.takeIf { it.isNotBlank() },
    groups = groups.mapNotNull { it.fromPrincipalGroup() }.toList().takeIf { it.isNotEmpty() } ?: emptyList(),
)

private fun String?.toPrincipalGroup(): GrschbrUserGroups? = when (this?.uppercase()) {
    "USER" -> GrschbrUserGroups.USER
    "ADMIN_CI" -> GrschbrUserGroups.ADMIN_CI
    "EXPERT" -> GrschbrUserGroups.EXPERT
    "TEST" -> GrschbrUserGroups.TEST
    // TODO сделать обработку ошибок
    else -> null
}

private fun GrschbrUserGroups?.fromPrincipalGroup(): String? = when (this) {
    GrschbrUserGroups.USER -> "USER"
    GrschbrUserGroups.ADMIN_CI -> "ADMIN_CI"
    GrschbrUserGroups.EXPERT -> "EXPERT"
    GrschbrUserGroups.TEST -> "TEST"
    // TODO сделать обработку ошибок
    else -> null
}
