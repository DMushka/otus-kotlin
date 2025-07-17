package com.otus.otuskotlin.groschenberry.biz.validation

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.errorValidation
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker

fun ICorChainDsl<GrschbrContext>.validateCopies(title: String) = worker {
    this.title = title
    on { type == GrschbrType.DETAIL && (cidValidated.copies < 0 || cidValidated.copies > 100000000) }
    handle {
        if (cidValidated.copies < 0)
            fail(
                errorValidation(
                    field = "copies",
                    violationCode = "negative",
                    description = "field must be positive"
                )
            )
        if (cidValidated.copies > 100000000)
            fail(
                errorValidation(
                    field = "copies",
                    violationCode = "tooBig",
                    description = "field must be less than 100000000 items"
                )
            )
    }
}
