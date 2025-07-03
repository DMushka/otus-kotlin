package com.otus.otuskotlin.groschenberry.common.models

import com.otus.otuskotlin.groschenberry.logging.common.LogLevel

data class GrschbrError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
    val level: LogLevel = LogLevel.ERROR
)
