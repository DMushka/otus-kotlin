package com.otus.otuskotlin.groschenberry.biz.repo

import com.otus.otuskotlin.groschenberry.api.log.mapper.toLog
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.UnExpectedDbError
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.common.repo.DbCIBRequest
import com.otus.otuskotlin.groschenberry.common.repo.DbCIDRequest
import com.otus.otuskotlin.groschenberry.common.repo.IDbCIResponse
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker

fun ICorChainDsl<GrschbrContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Добавление объявления в БД"
    on { state == GrschbrState.RUNNING && !(cibRepoPrepare.isEmpty() && cidRepoPrepare.isEmpty())}
    handle {
        logger.debug(
            msg = "Добавление объявления в БД started",
            marker = "BIZ",
            data =  this.toLog(command.toString())
        )
        var result: IDbCIResponse? = null
        when(type) {
            GrschbrType.BASIC -> result = ciRepo.createCIB(DbCIBRequest(cibRepoPrepare))
            GrschbrType.DETAIL -> result = ciRepo.createCID(DbCIDRequest(cidRepoPrepare))
            GrschbrType.NONE -> fail(UnExpectedDbError("Request"))
        }
        result?.let { processResult(it) }
        logger.debug(
            msg = "Добавление объявления в БД finished",
            marker = "BIZ",
            data =  this.toLog(command.toString())
        )
    }
}
