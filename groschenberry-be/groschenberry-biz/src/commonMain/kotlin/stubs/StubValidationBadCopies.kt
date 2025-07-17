package com.otus.otuskotlin.groschenberry.biz.stubs

import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrError
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.stubs.GrschbrStubs

fun ICorChainDsl<GrschbrContext>.stubValidationBadCopies(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для тиража в дополнительной карточки монеты
    """.trimIndent()
    on { stubCase == GrschbrStubs.BAD_COPIES && state == GrschbrState.RUNNING }
    handle {
        fail(
            GrschbrError(
                group = "validation",
                code = "validation-copies",
                field = "copies",
                message = "Wrong copies field"
            )
        )
    }
}
