package com.otus.otuskotlin.groschenberry.common

import com.otus.otuskotlin.groschenberry.common.repo.IRepoCI
import com.otus.otuskotlin.groschenberry.logging.common.GrbLoggerProvider

data class GrschbrCorSettings(
    val loggerProvider: GrbLoggerProvider = GrbLoggerProvider(),
    val repoStub: IRepoCI = IRepoCI.NONE,
    val repoTest: IRepoCI = IRepoCI.NONE,
    val repoProd: IRepoCI = IRepoCI.NONE,
) {
    companion object {
        val NONE = GrschbrCorSettings()
    }
}
