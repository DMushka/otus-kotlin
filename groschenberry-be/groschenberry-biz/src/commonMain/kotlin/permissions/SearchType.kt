package com.otus.otuskotlin.groschenberry.biz.permissions

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.GrschbrSearchPermissions
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrUserPermissions
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.chain
import com.otus.otuskotlin.groschenberry.cor.worker

fun ICorChainDsl<GrschbrContext>.searchTypes(title: String) = chain {
    this.title = title
    description = "Добавление ограничений в поисковый запрос согласно правам доступа и др. политикам"
    on { state == GrschbrState.RUNNING }
    worker("Определение типа поиска") {
        ciFilterValidated.searchPermissions = setOfNotNull(
            GrschbrSearchPermissions.ALL.takeIf { permissionsChain.contains(GrschbrUserPermissions.SEARCH) },
        ).toMutableSet()
    }
}
