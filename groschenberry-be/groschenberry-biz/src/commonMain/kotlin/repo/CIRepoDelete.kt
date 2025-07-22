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

fun ICorChainDsl<GrschbrContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление объявления из БД по ID"
    on { state == GrschbrState.RUNNING && !(cibRepoPrepare.isEmpty() && cidRepoPrepare.isEmpty())}
    handle {
        logger.debug(
            msg = "Удаление объявления из БД по ID started",
            marker = "BIZ",
            data =  this.toLog(command.toString())
        )
        var result: IDbCIResponse? = null
        when(type) {
            GrschbrType.BASIC -> result = ciRepo.deleteCIB(DbCIIdRequest(cibValidated))
            GrschbrType.DETAIL -> result = ciRepo.deleteCID(DbCIIdRequest(cidValidated))
            GrschbrType.NONE -> fail(UnExpectedDbError("Request"))
        }
        result?.let { processResult(it) }
        logger.debug(
            msg = "Удаление объявления из БД по ID finished",
            marker = "BIZ",
            data =  this.toLog(command.toString())
        )
    }
}
