package com.otus.otuskotlin.groschenberry.biz.validation

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker

fun ICorChainDsl<GrschbrContext>.validateDescription(title: String, regex: Regex) = worker {
    this.title = title
    on { (type == GrschbrType.BASIC && (cibValidated.description.isEmpty() || !cibValidated.description.contains(regex)))
            || (type == GrschbrType.DETAIL && (cidValidated.description.isEmpty() || !cidValidated.description.contains(regex)))}
    handle {
        when(type) {
            GrschbrType.BASIC -> validateStringField("description", cibValidated.description, regex)
            GrschbrType.DETAIL -> validateStringField("description", cidValidated.description, regex)
            GrschbrType.NONE -> null
        }
    }
}
