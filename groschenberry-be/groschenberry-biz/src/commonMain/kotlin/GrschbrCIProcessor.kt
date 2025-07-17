package com.otus.otuskotlin.groschenberry.biz

import com.otus.otuskotlin.groschenberry.biz.general.initStatus
import com.otus.otuskotlin.groschenberry.biz.general.operation
import com.otus.otuskotlin.groschenberry.biz.general.stubs
import com.otus.otuskotlin.groschenberry.biz.stubs.*
import com.otus.otuskotlin.groschenberry.biz.validation.REG_EXP_CONTENT
import com.otus.otuskotlin.groschenberry.biz.validation.REG_EXP_ID
import com.otus.otuskotlin.groschenberry.biz.validation.validateCIBId
import com.otus.otuskotlin.groschenberry.biz.validation.validateCopies
import com.otus.otuskotlin.groschenberry.biz.validation.validateDescription
import com.otus.otuskotlin.groschenberry.biz.validation.validateDiameter
import com.otus.otuskotlin.groschenberry.biz.validation.validateId
import com.otus.otuskotlin.groschenberry.biz.validation.validateIssueYear
import com.otus.otuskotlin.groschenberry.biz.validation.validateLock
import com.otus.otuskotlin.groschenberry.biz.validation.validateMaterial
import com.otus.otuskotlin.groschenberry.biz.validation.validateMint
import com.otus.otuskotlin.groschenberry.biz.validation.validateSearchStringLength
import com.otus.otuskotlin.groschenberry.biz.validation.validateStartYear
import com.otus.otuskotlin.groschenberry.biz.validation.validateStopYear
import com.otus.otuskotlin.groschenberry.biz.validation.validateTitle
import com.otus.otuskotlin.groschenberry.biz.validation.validation
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.GrschbrCorSettings
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCommand
import com.otus.otuskotlin.groschenberry.cor.rootChain

class GrschbrCIProcessor(
    private val corSettings: GrschbrCorSettings = GrschbrCorSettings.NONE
) {
    suspend fun exec(ctx: GrschbrContext) = businessChain.exec(ctx.also { it.corSettings = corSettings })

    private val businessChain = rootChain {
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

            validation {
                validateTitle("Валидация заголовка", REG_EXP_CONTENT)
                validateDescription("Валидация описания", REG_EXP_CONTENT)
                //validateCountry("Валидация страны")
                //validateCurrency("Валидация валюты")
                //validateNominal("Валидация номинала")
                validateMaterial("Валидация материала", REG_EXP_CONTENT)
                validateDiameter("Валидация диаметра")
                validateStartYear("Валидация года начала выпуска")
                validateStopYear("Валидация года конца выпуска")
                validateMint("Валидация монетного двора", REG_EXP_CONTENT)
                validateCopies("Валидация тиража")
                validateIssueYear("Валидация года выпуска")
                validateCIBId("Валидация cibId", REG_EXP_ID)
            }
        }
        operation("Получить карточку монеты", GrschbrCommand.READ) {
            stubs("Обработка стабов") {
                stubReadSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                validateId("Проверка id", REG_EXP_ID)
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
            validation {
                validateTitle("Валидация заголовка", REG_EXP_CONTENT)
                validateDescription("Валидация описания", REG_EXP_CONTENT)
                //validateCountry("Валидация страны")
                //validateCurrency("Валидация валюты")
                //validateNominal("Валидация номинала")
                validateMaterial("Валидация материала", REG_EXP_CONTENT)
                validateDiameter("Валидация диаметра")
                validateStartYear("Валидация года начала выпуска")
                validateStopYear("Валидация года конца выпуска")
                validateMint("Валидация монетного двора", REG_EXP_CONTENT)
                validateCopies("Валидация тиража")
                validateIssueYear("Валидация года выпуска")
                validateId("Проверка id", REG_EXP_ID)
                validateCIBId("Валидация cibId", REG_EXP_ID)
                validateLock("Проверка lock", REG_EXP_ID)
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
            validation {
                validateId("Проверка id", REG_EXP_ID)
                validateLock("Проверка lock", REG_EXP_ID)
            }
        }
        operation("Поиск карточки монеты", GrschbrCommand.SEARCH) {
            stubs("Обработка стабов") {
                stubSearchSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadSearchString("Имитация ошибки валидации поисковой строки")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                validateSearchStringLength("Валидация длины строки поиска в фильтре")
            }
        }
    }.build()
}
