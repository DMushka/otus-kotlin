package com.otus.otuskotlin.groschenberry.biz

import com.otus.otuskotlin.groschenberry.biz.general.initStatus
import com.otus.otuskotlin.groschenberry.biz.general.operation
import com.otus.otuskotlin.groschenberry.biz.general.stubs
import com.otus.otuskotlin.groschenberry.biz.stubs.*
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.GrschbrCorSettings
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCommand
import com.otus.otuskotlin.groschenberry.cor.rootChain

class GrschbrCIProcessor(
    private val corSettings: GrschbrCorSettings = GrschbrCorSettings.NONE
) {
    suspend fun exec(ctx: GrschbrContext) = businessChain.exec(ctx.also { it.corSettings = corSettings })

    private val businessChain = rootChain<GrschbrContext> {
        initStatus("Инициализация статуса")

        operation("Создание карточки монеты", GrschbrCommand.CREATE) {
            stubs("Обработка стабов") {
                stubCreateSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadTitle("Имитация ошибки валидации заголовка")
                stubValidationBadDescription("Имитация ошибки валидации описания")
                stubValidationBadCountry("Имитация ошибки валидации страны")
                stubValidationBadCurrency("Имитация ошибки валидации валюты")
                stubValidationBadNominal("Имитация ошибки валидации номинала")
                stubValidationBadMaterial("Имитация ошибки валидации материала")
                stubValidationBadDiameter("Имитация ошибки валидации диаметра")
                stubValidationBadStartYear("Имитация ошибки валидации года начала выпуска")
                stubValidationBadStopYear("Имитация ошибки валидации года конца выпуска")
                stubValidationBadMint("Имитация ошибки валидации монетного двора")
                stubValidationBadCopies("Имитация ошибки валидации тиража")
                stubValidationBadIssueYear("Имитация ошибки валидации года выпуска")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }
        operation("Получить карточку монеты", GrschbrCommand.READ) {
            stubs("Обработка стабов") {
                stubReadSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }
        operation("Изменить карточку монеты", GrschbrCommand.UPDATE) {
            stubs("Обработка стабов") {
                stubUpdateSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubValidationBadTitle("Имитация ошибки валидации заголовка")
                stubValidationBadDescription("Имитация ошибки валидации описания")
                stubValidationBadCountry("Имитация ошибки валидации страны")
                stubValidationBadCurrency("Имитация ошибки валидации валюты")
                stubValidationBadNominal("Имитация ошибки валидации номинала")
                stubValidationBadMaterial("Имитация ошибки валидации материала")
                stubValidationBadDiameter("Имитация ошибки валидации диаметра")
                stubValidationBadStartYear("Имитация ошибки валидации года начала выпуска")
                stubValidationBadStopYear("Имитация ошибки валидации года конца выпуска")
                stubValidationBadMint("Имитация ошибки валидации монетного двора")
                stubValidationBadCopies("Имитация ошибки валидации тиража")
                stubValidationBadIssueYear("Имитация ошибки валидации года выпуска")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }
        operation("Удалить карточку монеты", GrschbrCommand.DELETE) {
            stubs("Обработка стабов") {
                stubDeleteSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubCanNotDelete("Имитация ошибки удаления")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }
        operation("Поиск карточки монеты", GrschbrCommand.SEARCH) {
            stubs("Обработка стабов") {
                stubSearchSuccess("Имитация успешной обработки", corSettings)
                //stubValidationBadId("Имитация ошибки валидации id")
                stubValidationBadSearchString("Имитация ошибки валидации поисковой строки")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }
    }.build()
}
