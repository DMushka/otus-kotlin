package com.otus.otuskotlin.groschenberry.common.helpers

import com.otus.otuskotlin.groschenberry.common.models.GrschbrError

fun Throwable.asGrschbrError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = GrschbrError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)
