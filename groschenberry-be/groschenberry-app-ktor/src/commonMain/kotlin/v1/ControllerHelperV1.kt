package com.otus.otuskotlin.groschenberry.app.ktor.v1

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import com.otus.otuskotlin.groschenberry.api.v1.mappers.*
import com.otus.otuskotlin.groschenberry.api.v1.models.IBasicRequest
import com.otus.otuskotlin.groschenberry.api.v1.models.IBasicResponse
import com.otus.otuskotlin.groschenberry.api.v1.models.IDetailRequest
import com.otus.otuskotlin.groschenberry.api.v1.models.IDetailResponse
import com.otus.otuskotlin.groschenberry.app.common.controllerHelper
import com.otus.otuskotlin.groschenberry.app.ktor.GrschbrAppSettings
import kotlin.reflect.KClass

suspend inline fun <reified Q : IBasicRequest, @Suppress("unused") reified R : IBasicResponse> ApplicationCall.processCIB(
    appSettings: GrschbrAppSettings,
    clazz: KClass<*>,
    logId: String,
) = appSettings.controllerHelper(
    {
        fromTransport(this@processCIB.receive<Q>())
    },
    { this@processCIB.respond(toTransportCIB() as R) },
    clazz,
    logId,
)

suspend inline fun <reified Q : IDetailRequest, @Suppress("unused") reified R : IDetailResponse> ApplicationCall.processCID(
    appSettings: GrschbrAppSettings,
    clazz: KClass<*>,
    logId: String,
) = appSettings.controllerHelper(
    {
        fromTransport(this@processCID.receive<Q>())
    },
    { this@processCID.respond(toTransportCID() as R) },
    clazz,
    logId,
)