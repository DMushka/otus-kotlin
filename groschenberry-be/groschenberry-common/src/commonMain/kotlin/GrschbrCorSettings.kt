package com.otus.otuskotlin.groschenberry.common

import com.otus.otuskotlin.groschenberry.logging.common.GrbLoggerProvider

data class GrschbrCorSettings(
    val loggerProvider: GrbLoggerProvider = GrbLoggerProvider(),
) {
    companion object {
        val NONE = GrschbrCorSettings()
    }
}
