package com.otus.otuskotlin.groschenberry.biz.validation

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker

fun ICorChainDsl<GrschbrContext>.validateMint(title: String, regex: Regex) = worker {
    this.title = title
    on { type == GrschbrType.DETAIL && cidValidated.mint.isNotEmpty() && !cidValidated.mint.contains(regex)}
    handle {
        validateStringField("mint", cidValidated.mint, regex)
    }
}
