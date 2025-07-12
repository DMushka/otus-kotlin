package com.otus.otuskotlin.groschenberry.biz.validation

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker

fun ICorChainDsl<GrschbrContext>.validateId(title: String, regex: Regex) = worker {
    this.title = title
    on { (type == GrschbrType.BASIC && (cibValidated.id.asString().isEmpty() || !cibValidated.id.asString().contains(regex)))
            || (type == GrschbrType.DETAIL && (cidValidated.id.asString().isEmpty() || !cidValidated.id.asString().contains(regex)))}
    handle {
        when(type) {
            GrschbrType.BASIC -> validateStringField("id", cibValidated.id.asString(), regex)
            GrschbrType.DETAIL -> validateStringField("id", cidValidated.id.asString(), regex)
            GrschbrType.NONE -> null
        }
    }
}
