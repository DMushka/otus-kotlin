package com.otus.otuskotlin.groschenberry.biz.repo

import com.otus.otuskotlin.groschenberry.api.log.mapper.toLog
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.UnExpectedDbError
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.common.repo.DbCIIdRequest
import com.otus.otuskotlin.groschenberry.common.repo.IDbCIResponse
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker

fun ICorChainDsl<GrschbrContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение объявления из БД"
    on { state == GrschbrState.RUNNING }
    handle {
        logger.debug(
            msg = "Чтение объявления из БД по ID started",
            marker = "BIZ",
            data =  this.toLog(command.toString())
        )
        var result: IDbCIResponse? = null
        when(type) {
            GrschbrType.BASIC -> result = ciRepo.readCIB(DbCIIdRequest(cibValidated))
            GrschbrType.DETAIL -> result = ciRepo.readCID(DbCIIdRequest(cidValidated))
            GrschbrType.NONE -> fail(UnExpectedDbError("Request"))
        }
        result?.let { processResult(it) }
        logger.debug(
            msg = "Чтение объявления из БД по ID finished",
            marker = "BIZ",
            data =  this.toLog(command.toString())
        )
    }
}