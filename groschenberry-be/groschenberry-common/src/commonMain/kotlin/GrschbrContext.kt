package com.otus.otuskotlin.groschenberry.common

import kotlinx.datetime.Instant
import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrPrincipalModel
import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrUserPermissions
import com.otus.otuskotlin.groschenberry.common.repo.IRepoCI
import com.otus.otuskotlin.groschenberry.common.stubs.GrschbrStubs
import com.otus.otuskotlin.groschenberry.logging.common.IGrbLogWrapper

data class GrschbrContext(

    var corSettings: GrschbrCorSettings = GrschbrCorSettings(),
    var logger: IGrbLogWrapper = corSettings.loggerProvider.logger("GrschbrContext"),

    var command: GrschbrCommand = GrschbrCommand.NONE,
    var state: GrschbrState = GrschbrState.NONE,
    val errors: MutableList<GrschbrError> = mutableListOf(),

    var type: GrschbrType = GrschbrType.NONE,

    var workMode: GrschbrWorkMode = GrschbrWorkMode.PROD,
    var stubCase: GrschbrStubs = GrschbrStubs.NONE,

    var principal: GrschbrPrincipalModel = GrschbrPrincipalModel.NONE,
    val permissionsChain: MutableSet<GrschbrUserPermissions> = mutableSetOf(),
    var permitted: Boolean = false,

    var requestId: GrschbrRequestId = GrschbrRequestId.NONE,
    var timeStart: Instant = Instant.NONE,

    var cibRequest: GrschbrCIB = GrschbrCIB(),
    var cibResponse: GrschbrCIB = GrschbrCIB(),
    var cibsResponse: MutableList<GrschbrCIB> = mutableListOf(),
    var cibValidated: GrschbrCIB = GrschbrCIB(),

    var cidRequest: GrschbrCID = GrschbrCID(),
    var cidResponse: GrschbrCID = GrschbrCID(),
    var cidsResponse: MutableList<GrschbrCID> = mutableListOf(),
    var cidValidated: GrschbrCID = GrschbrCID(),

    var ciFilterRequest: GrschbrCIFilter = GrschbrCIFilter(),
    var ciFilterValidated: GrschbrCIFilter = GrschbrCIFilter(),

    var ciRepo: IRepoCI = IRepoCI.NONE,
    //var cibRepo: IRepoCIB = IRepoCIB.NONE,
    var cibRepoRead: GrschbrCIB = GrschbrCIB(), // То, что прочитали из репозитория
    var cibRepoPrepare: GrschbrCIB = GrschbrCIB(), // То, что готовим для сохранения в БД
    var cibRepoDone: GrschbrCIB = GrschbrCIB(),  // Результат, полученный из БД
    var cibsRepoDone: MutableList<GrschbrCIB> = mutableListOf(),
    //var cidRepo: IRepoCID = IRepoCID.NONE,
    var cidRepoRead: GrschbrCID = GrschbrCID(), // То, что прочитали из репозитория
    var cidRepoPrepare: GrschbrCID = GrschbrCID(), // То, что готовим для сохранения в БД
    var cidRepoDone: GrschbrCID = GrschbrCID(),  // Результат, полученный из БД
    var cidsRepoDone: MutableList<GrschbrCID> = mutableListOf(),
    )
