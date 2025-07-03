package com.otus.otuskotlin.groschenberry.app.ktor.plugins

import io.ktor.server.application.*
import com.otus.otuskotlin.groschenberry.logging.common.GrbLoggerProvider

expect fun Application.getLoggerProviderConf(): GrbLoggerProvider
