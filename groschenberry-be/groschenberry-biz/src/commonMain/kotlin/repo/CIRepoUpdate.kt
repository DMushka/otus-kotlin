package com.otus.otuskotlin.groschenberry.biz.repo

import com.otus.otuskotlin.groschenberry.api.log.mapper.toLog
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.UnExpectedDbError
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCI
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIB
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.common.repo.DbCIBRequest
import com.otus.otuskotlin.groschenberry.common.repo.DbCIResponseErr
import com.otus.otuskotlin.groschenberry.common.repo.DbCIBResponseErrWithData
import com.otus.otuskotlin.groschenberry.common.repo.DbCIBResponseOk
import com.otus.otuskotlin.groschenberry.common.repo.DbCIDRequest
import com.otus.otuskotlin.groschenberry.common.repo.DbCIDResponseErrWithData
import com.otus.otuskotlin.groschenberry.common.repo.DbCIDResponseOk
import com.otus.otuskotlin.groschenberry.common.repo.IDbCIResponse
import com.otus.otuskotlin.groschenberry.common.repo.IDbRequest
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker

fun ICorChainDsl<GrschbrContext>.repoUpdate(title: String) = worker {
    this.title = title
    on { state == GrschbrState.RUNNING && !(cibRepoPrepare.isEmpty() && cidRepoPrepare.isEmpty())}
    handle {
        logger.debug(
            msg = "Изменение объявления в БД по ID started",
            marker = "BIZ",
            data =  this.toLog(command.toString())
        )
        var result: IDbCIResponse? = null
        when(type) {
            GrschbrType.BASIC -> result = ciRepo.updateCIB(DbCIBRequest(cibRepoPrepare))
            GrschbrType.DETAIL -> result = ciRepo.updateCID(DbCIDRequest(cidRepoPrepare))
            GrschbrType.NONE -> fail(UnExpectedDbError("Request"))
        }
        result?.let { processResult(it) }
        logger.debug(
            msg = "Изменение объявления из БД по ID finished",
            marker = "BIZ",
            data =  this.toLog(command.toString())
        )
    }
}
