package com.otus.otuskotlin.groschenberry.biz.validation

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.errorValidation
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker

fun ICorChainDsl<GrschbrContext>.validateTitle(title: String, regex: Regex) = worker {
    this.title = title
    on { type == GrschbrType.BASIC && (cibValidated.title.isEmpty() || !cibValidated.title.contains(regex))}
    handle {
        validateStringField("title", cibValidated.title, regex)
    }
}
