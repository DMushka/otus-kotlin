package com.otus.otuskotlin.groschenberry.biz.stubs

import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.GrschbrCorSettings
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.models.models.GrschbrType
import com.otus.otuskotlin.groschenberry.common.stubs.GrschbrStubs
import com.otus.otuskotlin.groschenberry.logging.common.LogLevel
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub

fun ICorChainDsl<GrschbrContext>.stubReadSuccess(title: String, corSettings: GrschbrCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для чтения основной карточки монеты
    """.trimIndent()
    on { stubCase == GrschbrStubs.SUCCESS && state == GrschbrState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubOffersSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            state = GrschbrState.FINISHING
            when(type) {
                GrschbrType.BASIC -> cibResponse = GrschbrCIBStub.prepareResult {
                    cibRequest.id.takeIf { it != GrschbrCIId.NONE }?.also { this.id = it }
                }
                GrschbrType.DETAIL -> cidResponse = GrschbrCIDStub.prepareResult {
                    cidRequest.id.takeIf { it != GrschbrCIId.NONE }?.also { this.id = it }
                }
                GrschbrType.NONE -> logger.log("Unknown CI type")
            }
        }
    }
}
