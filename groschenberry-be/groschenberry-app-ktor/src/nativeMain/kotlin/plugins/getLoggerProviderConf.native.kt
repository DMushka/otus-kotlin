package com.otus.otuskotlin.groschenberry.app.ktor.plugins

import io.ktor.server.application.*
import com.otus.otuskotlin.groschenberry.logging.common.GrbLoggerProvider
import com.otus.otuskotlin.groschenberry.logging.kermit.grbLoggerKermit

actual fun Application.getLoggerProviderConf(): GrbLoggerProvider =
    when (val mode = environment.config.propertyOrNull("ktor.logger")?.getString()) {
        "kmp", null -> GrbLoggerProvider { grbLoggerKermit(it) }
        else -> throw Exception("Logger $mode is not allowed. Additted values are kmp and socket")
    }
