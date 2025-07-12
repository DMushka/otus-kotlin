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

fun ICorChainDsl<GrschbrContext>.validateIssueYear(title: String) = worker {
    val currentYear = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year
    this.title = title
    on { val intYear = cidValidated.issueYear.toIntOrNull()
        type == GrschbrType.DETAIL && cidValidated.issueYear.isNotEmpty() && cidValidated.issueYear != "0000"
            (intYear == null
                    || cidValidated.issueYear.length > 4
                    || intYear > currentYear
                    || intYear < -700)}
    handle {
        if (cidValidated.issueYear.length > 4)
            fail(
                errorValidation(
                    field = "issueYear",
                    violationCode = "invalidLength",
                    description = "field length must be less than 4"
                )
            )
        val intYear = cidValidated.issueYear.toIntOrNull()
        if (intYear == null)
            fail(
                errorValidation(
                    field = "issueYear",
                    violationCode = "notNumeric",
                    description = "field must be numeric"
                )
            )
        else if (intYear > currentYear)
            fail(
                errorValidation(
                    field = "issueYear",
                    violationCode = "inFuture",
                    description = "field must not be in future"
                )
            )
        else if (intYear < -700)
            fail(
                errorValidation(
                    field = "issueYear",
                    violationCode = "farInPast",
                    description = "field must not earlier than -700"
                )
            )
    }
}
