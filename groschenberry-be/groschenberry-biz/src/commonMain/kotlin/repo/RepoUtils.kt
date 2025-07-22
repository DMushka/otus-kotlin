package com.otus.otuskotlin.groschenberry.biz.repo

import com.otus.otuskotlin.groschenberry.api.log.mapper.toLog
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.UnExpectedDbError
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.common.repo.DbCIBResponseErrWithData
import com.otus.otuskotlin.groschenberry.common.repo.DbCIBResponseOk
import com.otus.otuskotlin.groschenberry.common.repo.DbCIDResponseErrWithData
import com.otus.otuskotlin.groschenberry.common.repo.DbCIDResponseOk
import com.otus.otuskotlin.groschenberry.common.repo.DbCIResponseErr
import com.otus.otuskotlin.groschenberry.common.repo.IDbCIResponse

inline fun GrschbrContext.processResult(result: IDbCIResponse) {

    logger.debug(
        msg = "Process Result",
        marker = "BIZ",
        data =  this.toLog(command.toString())
    )
    when(type) {
        GrschbrType.BASIC -> {
            when(result) {
                is DbCIBResponseOk -> cibRepoDone =  result.data
                is DbCIResponseErr -> fail(result.errors)
                is DbCIBResponseErrWithData -> {
                    fail(result.errors)
                    cibRepoDone = result.data
                }
                else -> fail(UnExpectedDbError("Response"))
            }
        }
        GrschbrType.DETAIL -> {
            when(result) {
                is DbCIDResponseOk -> cidRepoDone =  result.data
                is DbCIResponseErr -> fail(result.errors)
                is DbCIDResponseErrWithData -> {
                    fail(result.errors)
                    cidRepoDone = result.data
                }
                else -> fail(UnExpectedDbError("Response"))
            }
        }
        GrschbrType.NONE -> fail(UnExpectedDbError("Request"))
    }
}