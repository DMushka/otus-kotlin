package com.otus.otuskotlin.groschenberry.biz.stubs

import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrError
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.stubs.GrschbrStubs

fun ICorChainDsl<GrschbrContext>.stubValidationBadSearchString(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации поисковой строки
    """.trimIndent()
    on { stubCase == GrschbrStubs.BAD_SEARCH_STRING && state == GrschbrState.RUNNING }
    handle {
        fail(
            GrschbrError(
                group = "validation",
                code = "validation-search-string",
                field = "search-string",
                message = "Wrong search-string field"
            )
        )
    }
}
