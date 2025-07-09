package com.otus.otuskotlin.groschenberry.biz.stubs

import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrError
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.stubs.GrschbrStubs

fun ICorChainDsl<GrschbrContext>.stubValidationBadStopYear(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для года конца выпуска в основной карточки монеты
    """.trimIndent()
    on { stubCase == GrschbrStubs.BAD_STOP_YEAR && state == GrschbrState.RUNNING }
    handle {
        fail(
            GrschbrError(
                group = "validation",
                code = "validation-stop-year",
                field = "stop-year",
                message = "Wrong stop-year field"
            )
        )
    }
}
