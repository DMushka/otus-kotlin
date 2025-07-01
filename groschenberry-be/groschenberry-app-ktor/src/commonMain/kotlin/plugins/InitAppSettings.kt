package com.otus.otuskotlin.groschenberry.app.ktor.plugins

import io.ktor.server.application.*
import com.otus.otuskotlin.groschenberry.app.ktor.GrschbrAppSettings
import com.otus.otuskotlin.groschenberry.biz.GrschbrCIProcessor
import com.otus.otuskotlin.groschenberry.common.GrschbrCorSettings

fun Application.initAppSettings(): GrschbrAppSettings {
    val corSettings = GrschbrCorSettings(
        loggerProvider = getLoggerProviderConf(),
    )
    return GrschbrAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = GrschbrCIProcessor(corSettings),
    )
}
