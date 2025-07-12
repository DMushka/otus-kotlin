package com.otus.otuskotlin.groschenberry.biz.validation

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.errorValidation
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun ICorChainDsl<GrschbrContext>.validateStopYear(title: String) = worker {
    this.title = title
    on { val intYear = cibValidated.stopYear.toIntOrNull()
        type == GrschbrType.BASIC && cibValidated.stopYear.isNotEmpty() && cibValidated.stopYear != "0000"
            (intYear == null
                    || cibValidated.stopYear.length > 4
                    || intYear < -700)}
    handle {
        if (cibValidated.stopYear.length > 4)
            fail(
                errorValidation(
                    field = "stopYear",
                    violationCode = "invalidLength",
                    description = "field length must be less than 4"
                )
            )
        val intYear = cibValidated.stopYear.toIntOrNull()
        if (intYear == null)
            fail(
                errorValidation(
                    field = "stopYear",
                    violationCode = "notNumeric",
                    description = "field must be numeric"
                )
            )
        else if (intYear < -700)
            fail(
                errorValidation(
                    field = "stopYear",
                    violationCode = "farInPast",
                    description = "field must not earlier than -700"
                )
            )
    }
}
