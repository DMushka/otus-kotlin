package com.otus.otuskotlin.groschenberry.biz.repo

import com.otus.otuskotlin.groschenberry.api.log.mapper.toLog
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.UnExpectedDbError
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.common.repo.DbCIBsResponseOk
import com.otus.otuskotlin.groschenberry.common.repo.DbCIDsResponseOk
import com.otus.otuskotlin.groschenberry.common.repo.DbCIFilterRequest
import com.otus.otuskotlin.groschenberry.common.repo.DbCIsResponseErr
import com.otus.otuskotlin.groschenberry.common.repo.IDbCIsResponse
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker

fun ICorChainDsl<GrschbrContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск объявлений в БД по фильтру"
    on { state == GrschbrState.RUNNING }
    handle {
        logger.debug(
            msg = "Поиск объявлений в БД started",
            marker = "BIZ",
            data =  this.toLog(command.toString())
        )
        val request = DbCIFilterRequest(
            descriptionFilter = ciFilterValidated.searchString,
        )
        var result: IDbCIsResponse? = null
        when(type) {
            GrschbrType.BASIC -> result = ciRepo.searchCIB(request)
            GrschbrType.DETAIL -> result = ciRepo.searchCID(request)
            GrschbrType.NONE -> fail(UnExpectedDbError("Request"))
        }
        result?.let {
            when(type) {
                GrschbrType.BASIC -> {
                    when(result) {
                        is DbCIBsResponseOk -> cibsRepoDone = result.data.toMutableList()
                        is DbCIsResponseErr -> fail(result.errors)
                        else -> fail(UnExpectedDbError("Response"))
                    }
                }
                GrschbrType.DETAIL -> {
                    when(result) {
                        is DbCIDsResponseOk -> cidsRepoDone = result.data.toMutableList()
                        is DbCIsResponseErr -> fail(result.errors)
                        else -> fail(UnExpectedDbError("Response"))
                    }
                }
                GrschbrType.NONE -> fail(UnExpectedDbError("Response"))
            }
        }
        logger.debug(
            msg = "Поиск объявлений ив БД finished",
            marker = "BIZ",
            data =  this.toLog(command.toString())
        )
    }
}
