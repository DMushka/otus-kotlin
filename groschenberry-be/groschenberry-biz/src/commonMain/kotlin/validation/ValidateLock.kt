package com.otus.otuskotlin.groschenberry.biz.validation

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker

fun ICorChainDsl<GrschbrContext>.validateLock(title: String, regex: Regex) = worker {
    this.title = title
    on { (type == GrschbrType.BASIC && (cibValidated.lock.asString().isEmpty() || !cibValidated.lock.asString().contains(regex)))
            || (type == GrschbrType.DETAIL && (cidValidated.lock.asString().isEmpty() || !cidValidated.lock.asString().contains(regex)))}
    handle {
        when(type) {
            GrschbrType.BASIC -> validateStringField("lock", cibValidated.lock.asString(), regex)
            GrschbrType.DETAIL -> validateStringField("lock", cidValidated.lock.asString(), regex)
            GrschbrType.NONE -> null
        }
    }
}
