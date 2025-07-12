package com.otus.otuskotlin.groschenberry.common.helpers

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.GrschbrError
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.logging.common.LogLevel

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

inline fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: LogLevel = LogLevel.ERROR,
) = GrschbrError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)