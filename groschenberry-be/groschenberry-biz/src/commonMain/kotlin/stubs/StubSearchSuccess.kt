package com.otus.otuskotlin.groschenberry.biz.stubs

import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.GrschbrCorSettings
import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.common.models.models.GrschbrType
import com.otus.otuskotlin.groschenberry.common.stubs.GrschbrStubs
import com.otus.otuskotlin.groschenberry.logging.common.LogLevel
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub

fun ICorChainDsl<GrschbrContext>.stubSearchSuccess(title: String, corSettings: GrschbrCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для поиска объявлений
    """.trimIndent()
    on { stubCase == GrschbrStubs.SUCCESS && state == GrschbrState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubSearchSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            state = GrschbrState.FINISHING
            when(type) {
                GrschbrType.BASIC -> cibsResponse.addAll(GrschbrCIBStub.prepareSearchList(ciFilterRequest.searchString, GrschbrCountry.RUSSIA))
                GrschbrType.DETAIL -> cidsResponse.addAll(GrschbrCIDStub.prepareSearchList(ciFilterRequest.searchString, 100))
                GrschbrType.NONE -> logger.log("Unknown CI type")
            }
        }
    }
}
