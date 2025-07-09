package com.otus.otuskotlin.groschenberry.common.helpers

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.GrschbrError
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState

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

inline fun GrschbrContext.addError(vararg error: GrschbrError) = errors.addAll(error)

inline fun GrschbrContext.fail(error: GrschbrError) {
    addError(error)
    state = GrschbrState.FAILING
}