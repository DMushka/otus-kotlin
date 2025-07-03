package com.otus.otuskotlin.groschenberry.common

import kotlinx.datetime.Instant
import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.common.models.models.GrschbrType
import com.otus.otuskotlin.groschenberry.common.stubs.GrschbrStubs

data class GrschbrContext(
    var command: GrschbrCommand = GrschbrCommand.NONE,
    var state: GrschbrState = GrschbrState.NONE,
    val errors: MutableList<GrschbrError> = mutableListOf(),

    var type: GrschbrType = GrschbrType.NONE,

    var workMode: GrschbrWorkMode = GrschbrWorkMode.PROD,
    var stubCase: GrschbrStubs = GrschbrStubs.NONE,

    var requestId: GrschbrRequestId = GrschbrRequestId.NONE,
    var timeStart: Instant = Instant.NONE,

    var cibRequest: GrschbrCIB = GrschbrCIB(),
    var cibResponse: GrschbrCIB = GrschbrCIB(),
    var cibsResponse: MutableList<GrschbrCIB> = mutableListOf(),

    var cidRequest: GrschbrCID = GrschbrCID(),
    var cidResponse: GrschbrCID = GrschbrCID(),
    var cidsResponse: MutableList<GrschbrCID> = mutableListOf(),

    var ciFilterRequest: GrschbrCIFilter = GrschbrCIFilter(),
    )
