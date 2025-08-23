package com.otus.otuskotlin.groschenberry.common.helpers

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCommand
import com.otus.otuskotlin.groschenberry.common.models.GrschbrError
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrPrincipalModel
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

inline fun GrschbrContext.addError(error: GrschbrError) = errors.add(error)
inline fun GrschbrContext.addErrors(error: Collection<GrschbrError>) = errors.addAll(error)

inline fun GrschbrContext.fail(error: GrschbrError) {
    addError(error)
    state = GrschbrState.FAILING
}
inline fun GrschbrContext.fail(errors: Collection<GrschbrError>) {
    addErrors(errors)
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

inline fun errorSystem(
    violationCode: String,
    level: LogLevel = LogLevel.ERROR,
    e: Throwable,
) = GrschbrError(
    code = "system-$violationCode",
    group = "system",
    message = "System error occurred. Our stuff has been informed, please retry later",
    level = level,
    exception = e,
)

inline fun UnExpectedDbError(type: String) = GrschbrError(
    code = "db-$type",
    group = "db",
    message = "UnExpected Db $type"
)

inline fun accessViolation(
    principal: GrschbrPrincipalModel,
    operation: GrschbrCommand,
) = GrschbrError(
    code = "access-${operation.name.lowercase()}",
    group = "access",
    message = "User ${principal.genericName()} (${principal.id.asString()}) is not allowed to perform operation ${operation.name}",
    level = LogLevel.ERROR,
)