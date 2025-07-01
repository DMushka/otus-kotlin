package com.otus.otuskotlin.groschenberry.app.ktor.plugins

import com.otus.otuskotlin.groschenberry.logging.common.GrbLoggerProvider
import io.ktor.server.application.*
import com.otus.otuskotlin.groschenberry.logging.kermit.grbLoggerKermit

actual fun Application.getLoggerProviderConf(): GrbLoggerProvider =
    GrbLoggerProvider { grbLoggerKermit(it) }
