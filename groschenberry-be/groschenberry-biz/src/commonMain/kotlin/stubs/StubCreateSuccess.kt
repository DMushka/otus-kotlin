package com.otus.otuskotlin.groschenberry.biz.stubs

import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.GrschbrCorSettings
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCountry
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCurrency
import com.otus.otuskotlin.groschenberry.common.models.GrschbrNominal
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.common.stubs.GrschbrStubs
import com.otus.otuskotlin.groschenberry.logging.common.LogLevel
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub

fun ICorChainDsl<GrschbrContext>.stubCreateSuccess(title: String, corSettings: GrschbrCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для создания основной карточки монеты
    """.trimIndent()
    on { stubCase == GrschbrStubs.SUCCESS && state == GrschbrState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubCreatesSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            state = GrschbrState.FINISHED
            when(type) {
                GrschbrType.BASIC -> cibResponse =
                    GrschbrCIBStub.prepareResult {
                        cibRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
                        cibRequest.description.takeIf { it.isNotBlank() }?.also { this.description = it }
                        cibRequest.country.takeIf { it != GrschbrCountry.NONE }?.also { this.country = it }
                        cibRequest.currency.takeIf { it != GrschbrCurrency.NONE }?.also { this.currency = it }
                        cibRequest.nominal.takeIf { it != GrschbrNominal.NONE }?.also { this.nominal = it }
                        cibRequest.material.takeIf { it.isNotBlank()}?.also { this.material = it }
                        cibRequest.diameter.takeIf { it != 0.0 }?.also { this.diameter = it }
                        cibRequest.startYear.takeIf { it != "0000" }?.also { this.startYear = it }
                        cibRequest.stopYear.takeIf { it != "0000" }?.also { this.stopYear = it }
                    }
                GrschbrType.DETAIL -> cidResponse =
                    GrschbrCIDStub.prepareResult {
                        cidRequest.description.takeIf { it.isNotBlank() }?.also { this.description = it }
                        cidRequest.mint.takeIf { it.isNotBlank() }?.also { this.mint = it }
                        cidRequest.copies.takeIf { it != 0 }?.also { this.copies = it }
                        cidRequest.issueYear.takeIf { it != "0000"}?.also { this.issueYear = it }
                        cidRequest.cibId.takeIf { it != GrschbrCIId.NONE }?.also { this.cibId = it }
                    }
                GrschbrType.NONE -> logger.log("Unknown CI type")
            }
        }
    }
}
