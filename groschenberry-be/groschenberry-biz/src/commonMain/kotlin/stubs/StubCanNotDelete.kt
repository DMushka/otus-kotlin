package com.otus.otuskotlin.groschenberry.biz.stubs

import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrError
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.stubs.GrschbrStubs

fun ICorChainDsl<GrschbrContext>.stubCanNotDelete(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки удаления
    """.trimIndent()
    on { stubCase == GrschbrStubs.CANNOT_DELETE && state == GrschbrState.RUNNING }
    handle {
        fail(
            GrschbrError(
                group = "internal",
                code = "internal-can-not-delete",
                message = "Internal can not delete"
            )
        )
    }
}
