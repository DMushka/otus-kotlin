package com.otus.otuskotlin.groschenberry.biz.permissions

import com.otus.otuskotlin.groschenberry.api.log.mapper.toLog
import com.otus.otuskotlin.groschenberry.auth.checkPermitted
import com.otus.otuskotlin.groschenberry.auth.resolveRelationsTo
import com.otus.otuskotlin.groschenberry.biz.validation.validateStringField
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.accessViolation
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.chain
import com.otus.otuskotlin.groschenberry.cor.worker

fun ICorChainDsl<GrschbrContext>.accessValidation(title: String) = chain {
    this.title = title
    description = "Вычисление прав доступа по группе принципала и таблице прав доступа"
    on { state == GrschbrState.RUNNING }
    worker("Вычисление отношения объявления к принципалу") {
        when(type) {
            GrschbrType.BASIC -> cibRepoRead.principalRelations = cibRepoRead.resolveRelationsTo(principal)
            GrschbrType.DETAIL -> cidRepoRead.principalRelations = cidRepoRead.resolveRelationsTo(principal)
            GrschbrType.NONE -> null
        }
    }
    worker("Вычисление доступа к объявлению") {
        when(type) {
            GrschbrType.BASIC -> permitted = checkPermitted(command, cibRepoRead.principalRelations, permissionsChain)
            GrschbrType.DETAIL -> permitted = checkPermitted(command, cidRepoRead.principalRelations, permissionsChain)
            GrschbrType.NONE -> null
        }
        logger.debug(
            msg = "Вычисление прав на операцию finished",
            marker = "BIZ",
            data =  this.toLog("accessValidation")
        )
    }

    worker {
        this.title = "Валидация прав доступа"
        description = "Проверка наличия прав для выполнения операции"
        on { !permitted }
        handle {
            fail(
                accessViolation(
                    principal = principal,
                    operation = command,
                )
            )
            logger.error(
                msg = "Операция отклонена",
                marker = "BIZ",
                data =  this.toLog("accessValidation")
            )
        }
    }
}
