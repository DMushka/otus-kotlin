package com.otus.otuskotlin.groschenberry.biz.stubs

import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrError
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.stubs.GrschbrStubs

fun ICorChainDsl<GrschbrContext>.stubValidationBadNominal(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации номинала
    """.trimIndent()

    on { stubCase == GrschbrStubs.BAD_NOMINAL && state == GrschbrState.RUNNING }
    handle {
        fail(
            GrschbrError(
                group = "validation",
                code = "validation-nominal",
                field = "nominal",
                message = "Wrong nominal field"
            )
        )
    }
}
