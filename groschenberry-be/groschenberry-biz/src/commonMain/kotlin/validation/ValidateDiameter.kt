package com.otus.otuskotlin.groschenberry.biz.validation

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.errorValidation
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker

fun ICorChainDsl<GrschbrContext>.validateDiameter(title: String) = worker {
    this.title = title
    on { type == GrschbrType.BASIC && (cibValidated.diameter < 0 || cibValidated.diameter > 5000) }
    handle {
        if (cibValidated.diameter < 0)
            fail(
                errorValidation(
                    field = "diameter",
                    violationCode = "negative",
                    description = "field must be positive"
                )
            )
        if (cibValidated.diameter > 5000)
            fail(
                errorValidation(
                    field = "diameter",
                    violationCode = "tooBig",
                    description = "field must be less than 5000 mm"
                )
            )
    }
}
