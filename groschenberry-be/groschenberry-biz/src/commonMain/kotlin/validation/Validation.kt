package com.otus.otuskotlin.groschenberry.biz.validation

import com.otus.otuskotlin.groschenberry.api.log.mapper.toLog
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIB
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCID
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIFilter
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCILock
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.chain
import com.otus.otuskotlin.groschenberry.cor.worker

fun ICorChainDsl<GrschbrContext>.validation(block: ICorChainDsl<GrschbrContext>.() -> Unit) = chain {
    worker("Копируем поля в ciXValidated") {
        cibValidated = cibRequest.deepCopy()
        cidValidated = cidRequest.deepCopy()
        ciFilterValidated = ciFilterRequest.deepCopy()
    }

    worker("Нормализация данных") {
        logger.debug(
            msg = "Нормализация данных started",
            marker = "BIZ",
            data =  this.toLog(command.toString())
        )

        ciFilterValidated.searchString = ciFilterValidated.searchString.trim()
        when (type) {
            GrschbrType.BASIC -> {
                cibValidated.id = GrschbrCIId(cibValidated.id.asString().trim())
                cibValidated.title = cibValidated.title.trim()
                cibValidated.description = cibValidated.description.trim()
                cibValidated.material = cibValidated.material.trim()
                cibValidated.startYear = cibValidated.startYear.trim()
                cibValidated.stopYear = cibValidated.stopYear.trim()
                cibValidated.lock = GrschbrCILock(cibValidated.lock.asString().trim())
            }
            GrschbrType.DETAIL -> {
                cidValidated.id = GrschbrCIId(cidValidated.id.asString().trim())
                cidValidated.description = cidValidated.description.trim()
                cidValidated.mint = cidValidated.mint.trim()
                cidValidated.issueYear = cidValidated.issueYear.trim()
                cidValidated.cibId = GrschbrCIId(cidValidated.cibId .asString().trim())
                cidValidated.lock = GrschbrCILock(cidValidated.lock.asString().trim())
            }
            GrschbrType.NONE -> null
        }

        logger.debug(
            msg = "Нормализация данных finished",
            marker = "BIZ",
            data =  this.toLog(command.toString())
        )
    }

    block()

    worker("Завершение валидации") {
        if (state == GrschbrState.FAILING) {
            cibValidated = GrschbrCIB()
            cidValidated = GrschbrCID()
            ciFilterValidated = GrschbrCIFilter()
        }

        logger.info(
            msg = "Завершение валидации finished",
            marker = "BIZ",
            data =  this.toLog(command.toString())
        )
    }

    title = "Валидация"

    on { state == GrschbrState.RUNNING }
}