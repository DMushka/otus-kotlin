package com.otus.otuskotlin.groschenberry.biz.stubs

import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrError
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.stubs.GrschbrStubs

fun ICorChainDsl<GrschbrContext>.stubValidationBadStartYear(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для года начала выпуска в основной карточки монеты
    """.trimIndent()
    on { stubCase == GrschbrStubs.BAD_START_YEAR && state == GrschbrState.RUNNING }
    handle {
        fail(
            GrschbrError(
                group = "validation",
                code = "validation-start-year",
                field = "start-year",
                message = "Wrong start-year field"
            )
        )
    }
}
