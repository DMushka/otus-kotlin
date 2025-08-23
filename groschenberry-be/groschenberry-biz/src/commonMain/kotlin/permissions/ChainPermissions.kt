package com.otus.otuskotlin.groschenberry.biz.permissions

import com.otus.otuskotlin.groschenberry.api.log.mapper.toLog
import com.otus.otuskotlin.groschenberry.auth.resolveChainPermissions
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker


fun ICorChainDsl<GrschbrContext>.chainPermissions(title: String) = worker {
    this.title = title
    description = "Вычисление прав доступа для групп пользователей"

    on { state == GrschbrState.RUNNING }

    handle {
        permissionsChain.addAll(resolveChainPermissions(principal.groups))
        logger.debug(
            msg = "Вычисление прав доступа для групп пользователей",
            marker = "BIZ",
            data =  this.toLog("chainPermissions")
        )
    }
}
