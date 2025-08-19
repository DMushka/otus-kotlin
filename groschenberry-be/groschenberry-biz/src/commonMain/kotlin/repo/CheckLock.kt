package com.otus.otuskotlin.groschenberry.biz.repo

import com.otus.otuskotlin.groschenberry.api.log.mapper.toLog
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker
import com.otus.otuskotlin.groschenberry.common.repo.errorCIBRepoConcurrency
import com.otus.otuskotlin.groschenberry.common.repo.errorCIDRepoConcurrency

fun ICorChainDsl<GrschbrContext>.checkLock(title: String) = worker {
    this.title = title
    description = """
        Проверка оптимистичной блокировки. Если не равна сохраненной в БД, значит данные запроса устарели 
        и необходимо их обновить вручную
    """.trimIndent()
    on { state == GrschbrState.RUNNING && ((type == GrschbrType.BASIC && cibValidated.lock != cibRepoRead.lock) || (type == GrschbrType.DETAIL && cidValidated.lock != cidRepoRead.lock)) }
    handle {
        if (type == GrschbrType.BASIC)
            fail(
                errorCIBRepoConcurrency(cibRepoRead, cibValidated.lock)
                    .errors
            )
        else
            fail(
                errorCIDRepoConcurrency(cidRepoRead, cidValidated.lock)
                    .errors
            )
        logger.error(
            msg = "Проверка оптимистичной блокировки fails",
            marker = "BIZ",
            data =  this.toLog(command.toString())
        )
    }
}
