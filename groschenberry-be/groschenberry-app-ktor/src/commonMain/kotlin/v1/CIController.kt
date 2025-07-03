package com.otus.otuskotlin.groschenberry.app.ktor.v1

import io.ktor.server.application.*
import com.otus.otuskotlin.groschenberry.api.v1.models.*
import com.otus.otuskotlin.groschenberry.app.ktor.GrschbrAppSettings
import kotlin.reflect.KClass

val cibCreate: KClass<*> = ApplicationCall::createCIB::class
suspend fun ApplicationCall.createCIB(appSettings: GrschbrAppSettings) =
    processCIB<CIBCreateRequest, CIBCreateResponse>(appSettings, cibCreate,"create")

val cibRead: KClass<*> = ApplicationCall::readCIB::class
suspend fun ApplicationCall.readCIB(appSettings: GrschbrAppSettings) =
    processCIB<CIBReadRequest, CIBReadResponse>(appSettings, cibRead, "read")

val cibUpdate: KClass<*> = ApplicationCall::updateCIB::class
suspend fun ApplicationCall.updateCIB(appSettings: GrschbrAppSettings) =
    processCIB<CIBUpdateRequest, CIBUpdateResponse>(appSettings, cibUpdate, "update")

val cibDelete: KClass<*> = ApplicationCall::deleteCIB::class
suspend fun ApplicationCall.deleteCIB(appSettings: GrschbrAppSettings) =
    processCIB<CIBDeleteRequest, CIBDeleteResponse>(appSettings, cibDelete, "delete")

val cibSearch: KClass<*> = ApplicationCall::searchCIB::class
suspend fun ApplicationCall.searchCIB(appSettings: GrschbrAppSettings) =
    processCIB<CIBSearchRequest, CIBSearchResponse>(appSettings, cibSearch, "search")

val cidCreate: KClass<*> = ApplicationCall::createCID::class
suspend fun ApplicationCall.createCID(appSettings: GrschbrAppSettings) =
    processCID<CIDCreateRequest, CIDCreateResponse>(appSettings, cidCreate,"create")

val cidRead: KClass<*> = ApplicationCall::readCID::class
suspend fun ApplicationCall.readCID(appSettings: GrschbrAppSettings) =
    processCID<CIDReadRequest, CIDReadResponse>(appSettings, cidRead, "read")

val cidUpdate: KClass<*> = ApplicationCall::updateCID::class
suspend fun ApplicationCall.updateCID(appSettings: GrschbrAppSettings) =
    processCID<CIDUpdateRequest, CIDUpdateResponse>(appSettings, cidUpdate, "update")

val cidDelete: KClass<*> = ApplicationCall::deleteCID::class
suspend fun ApplicationCall.deleteCID(appSettings: GrschbrAppSettings) =
    processCID<CIDDeleteRequest, CIDDeleteResponse>(appSettings, cidDelete, "delete")

val cidSearch: KClass<*> = ApplicationCall::searchCID::class
suspend fun ApplicationCall.searchCID(appSettings: GrschbrAppSettings) =
    processCID<CIDSearchRequest, CIDSearchResponse>(appSettings, cidSearch, "search")

