package com.otus.otuskotlin.groschenberry.biz.repo

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.UnExpectedDbError
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker

fun ICorChainDsl<GrschbrContext>.repoPrepareCreate(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == GrschbrState.RUNNING }
    handle {
        when (type) {
            GrschbrType.BASIC -> {
                cibRepoPrepare = cibValidated.deepCopy()
                // TODO будет реализовано в занятии по управлению пользвателями
            }
            GrschbrType.DETAIL -> {
                cidRepoPrepare = cidValidated.deepCopy()
            }
            GrschbrType.NONE -> fail(UnExpectedDbError("Request"))
        }
    }
}
