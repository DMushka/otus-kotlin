package com.otus.otuskotlin.groschenberry.biz.validation

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.errorValidation
import com.otus.otuskotlin.groschenberry.common.helpers.fail

val REG_EXP_ID = Regex("^[0-9a-zA-Z-:]+$")
/*
  Проверяем, что у нас есть какие-то слова в строке,
  а не только бессмысленные символы типа %^&^$^%#^))&^*&%^^&
*/
val REG_EXP_CONTENT = Regex("\\p{L}")

inline fun GrschbrContext.validateStringField(fieldName: String, fieldValue: String, regex: Regex) {
    if (fieldValue.isEmpty()) {
        fail(
            errorValidation(
                field = fieldName,
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    } else if (!fieldValue.contains(regex))
        fail(
            errorValidation(
                field = fieldName,
                violationCode = "noContent",
                description = "field must contain letters"
            )
        )
}
