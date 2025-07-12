package com.otus.otuskotlin.groschenberry.biz.validation

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker

fun ICorChainDsl<GrschbrContext>.validateCIBId(title: String, regex: Regex) = worker {
    this.title = title
    on { type == GrschbrType.DETAIL && (cidValidated.cibId.asString().isEmpty() || !cidValidated.cibId.asString().contains(regex)) }
    handle {
        validateStringField("cibId", cidValidated.cibId.asString(), regex)
    }
}
