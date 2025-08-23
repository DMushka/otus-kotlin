package com.otus.otuskotlin.groschenberry.biz.repo

import com.otus.otuskotlin.groschenberry.biz.exception.GrschbrCIDbNotConfiguredException
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.errorSystem
import com.otus.otuskotlin.groschenberry.common.helpers.fail
import com.otus.otuskotlin.groschenberry.common.models.GrschbrWorkMode
import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrUserGroups
import com.otus.otuskotlin.groschenberry.common.repo.IRepoCI
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker

fun ICorChainDsl<GrschbrContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от зпрошенного режима работы        
    """.trimIndent()
    handle {
        ciRepo = when {
            workMode == GrschbrWorkMode.TEST -> corSettings.repoTest
            workMode == GrschbrWorkMode.STUB -> corSettings.repoStub
            principal.groups.contains(GrschbrUserGroups.TEST) -> corSettings.repoTest
            else -> corSettings.repoProd
        }
        if (workMode != GrschbrWorkMode.STUB && ciRepo == IRepoCI.NONE) fail(
            errorSystem(
                violationCode = "dbNotConfigured",
                e = GrschbrCIDbNotConfiguredException(workMode)
            )
        )
    }
}
