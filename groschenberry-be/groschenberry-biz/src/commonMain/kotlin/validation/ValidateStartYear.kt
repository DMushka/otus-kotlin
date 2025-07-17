package com.otus.otuskotlin.groschenberry.biz.validation

import com.otus.otuskotlin.groschenberry.api.log.mapper.toLog
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.errorValidation
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun ICorChainDsl<GrschbrContext>.validateStartYear(title: String) = worker {
    val currentYear = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year
    this.title = title
    on { val intYear = cibValidated.startYear.toIntOrNull()
        type == GrschbrType.BASIC && cibValidated.startYear.isNotEmpty() && cibValidated.startYear != "0000"
            (intYear == null
                    || cibValidated.startYear.length > 4
                    || intYear > currentYear
                    || intYear < -700)}
    handle {
        if (cibValidated.startYear.length > 4)
            fail(
                errorValidation(
                    field = "startYear",
                    violationCode = "invalidLength",
                    description = "field length must be less than 4"
                )
            )
        val intYear = cibValidated.startYear.toIntOrNull()
        if (intYear == null)
            fail(
                errorValidation(
                    field = "startYear",
                    violationCode = "notNumeric",
                    description = "field must be numeric"
                )
            )
        else if (intYear > currentYear)
            fail(
                errorValidation(
                    field = "startYear",
                    violationCode = "inFuture",
                    description = "field must not be in future"
                )
            )
        else if (intYear < -700)
            fail(
                errorValidation(
                    field = "startYear",
                    violationCode = "farInPast",
                    description = "field must not be earlier than -700"
                )
            )
    }
}
