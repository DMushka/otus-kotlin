package com.otus.otuskotlin.groschenberry.biz.repo

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.UnExpectedDbError
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker

fun ICorChainDsl<GrschbrContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
            "и данные, полученные от пользователя"
    on { state == GrschbrState.RUNNING }
    handle {
        when (type) {
            GrschbrType.BASIC -> {
                cibRepoPrepare = cibRepoRead.deepCopy().apply {
                    id = cibValidated.id
                    this.title = cibValidated.title
                    description = cibValidated.description
                    country = cibValidated.country
                    currency = cibValidated.currency
                    material = cibValidated.material
                    nominal = cibValidated.nominal
                    startYear = cibValidated.startYear
                    stopYear = cibValidated.stopYear
                }
            }
            GrschbrType.DETAIL -> {
                cidRepoPrepare = cidRepoRead.deepCopy().apply {
                    id = cidValidated.id
                    description = cidValidated.description
                    mint = cidValidated.mint
                    copies = cidValidated.copies
                    issueYear = cidValidated.issueYear
                    cibId = cidValidated.cibId
                }
            }
            GrschbrType.NONE -> fail(UnExpectedDbError("Request"))
        }
    }
}
