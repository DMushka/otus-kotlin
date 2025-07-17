package com.otus.otuskotlin.groschenberry.biz.validation

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.errorValidation
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.chain
import com.otus.otuskotlin.groschenberry.cor.worker

fun ICorChainDsl<GrschbrContext>.validateSearchStringLength(title: String) = chain {
    this.title = title
    this.description = """
        Валидация длины строки поиска в поисковых фильтрах. Допустимые значения:
        - null - не выполняем поиск по строке
        - 3-100 - допустимая длина
        - больше 100 - слишком длинная строка
    """.trimIndent()
    on { state == GrschbrState.RUNNING }
    worker {
        this.title = "Проверка кейса длины на 0-2 символа"
        this.description = this.title
        on { state == GrschbrState.RUNNING && ciFilterValidated.searchString.length in (1..2) }
        handle {
            fail(
                errorValidation(
                    field = "searchString",
                    violationCode = "tooShort",
                    description = "Search string must contain at least 3 symbols"
                )
            )
        }
    }
    worker {
        this.title = "Проверка кейса длины на более 100 символов"
        this.description = this.title
        on { state == GrschbrState.RUNNING && ciFilterValidated.searchString.length > 100 }
        handle {
            fail(
                errorValidation(
                    field = "searchString",
                    violationCode = "tooLong",
                    description = "Search string must be no more than 100 symbols long"
                )
            )
        }
    }
}
