package com.otus.otuskotlin.groschenberry.biz.repo

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.models.GrschbrWorkMode
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker

fun ICorChainDsl<GrschbrContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != GrschbrWorkMode.STUB }
    handle {
        cibResponse = cibRepoDone
        cidResponse = cidRepoDone
        cibsResponse = cibsRepoDone
        cidsResponse = cidsRepoDone
        state = when (val st = state) {
            GrschbrState.RUNNING -> GrschbrState.FINISHED
            else -> st
        }
    }
}
