package com.otus.otuskotlin.groschenberry.biz.stubs

import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrError
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState

fun ICorChainDsl<GrschbrContext>.stubNoCase(title: String) = worker {
    this.title = title
    this.description = """
        Валидируем ситуацию, когда запрошен кейс, который не поддерживается в стабах
    """.trimIndent()
    on { state == GrschbrState.RUNNING }
    handle {
        fail(
            GrschbrError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}
