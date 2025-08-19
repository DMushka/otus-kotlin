package com.otus.otuskotlin.groschenberry.app.ktor.plugins

import io.ktor.server.application.*
import com.otus.otuskotlin.groschenberry.app.ktor.GrschbrAppSettings
import com.otus.otuskotlin.groschenberry.backend.repository.inmemory.CIRepoStub
import com.otus.otuskotlin.groschenberry.biz.GrschbrCIProcessor
import com.otus.otuskotlin.groschenberry.common.GrschbrCorSettings
//import com.otus.otuskotlin.groschenberry.repo.inmemory.CIRepoInMemory

fun Application.initAppSettings(): GrschbrAppSettings {
    val corSettings = GrschbrCorSettings(
        loggerProvider = getLoggerProviderConf(),
        repoTest = getDatabaseConf(CIDbType.TEST),//CIRepoInMemory(),
        repoProd = getDatabaseConf(CIDbType.PROD),//CIRepoInMemory(),
        repoStub = CIRepoStub(),
    )
    return GrschbrAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = GrschbrCIProcessor(corSettings),
    )
}
