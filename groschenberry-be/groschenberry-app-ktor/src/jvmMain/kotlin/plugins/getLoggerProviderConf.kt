package com.otus.otuskotlin.groschenberry.app.ktor.plugins

import io.ktor.server.application.*
import com.otus.otuskotlin.groschenberry.logging.common.GrbLoggerProvider
import com.otus.otuskotlin.groschenberry.logging.jvm.grbLoggerLogback
import com.otus.otuskotlin.groschenberry.logging.kermit.grbLoggerKermit

actual fun Application.getLoggerProviderConf(): GrbLoggerProvider =
    when (val mode = environment.config.propertyOrNull("ktor.logger")?.getString()) {
        "kmp" -> GrbLoggerProvider { grbLoggerKermit(it) }
        "logback", null -> GrbLoggerProvider { grbLoggerLogback(it) }
        else -> throw Exception("Logger $mode is not allowed. Additted values are kmp, socket and logback (default)")
}
