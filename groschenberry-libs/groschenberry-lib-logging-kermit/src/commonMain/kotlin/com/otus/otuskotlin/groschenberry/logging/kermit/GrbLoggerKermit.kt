package com.otus.otuskotlin.groschenberry.logging.kermit

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.StaticConfig
import com.otus.otuskotlin.groschenberry.logging.common.IGrbLogWrapper
import kotlin.reflect.KClass

@Suppress("unused")
fun grbLoggerKermit(loggerId: String): IGrbLogWrapper {
    val logger = Logger(
        config = StaticConfig(
            minSeverity = Severity.Info,
        ),
        tag = "DEV"
    )
    return GrbLoggerWrapperKermit(
        logger = logger,
        loggerId = loggerId,
    )
}

@Suppress("unused")
fun gbrLoggerKermit(cls: KClass<*>): IGrbLogWrapper {
    val logger = Logger(
        config = StaticConfig(
            minSeverity = Severity.Info,
        ),
        tag = "DEV"
    )
    return GrbLoggerWrapperKermit(
        logger = logger,
        loggerId = cls.qualifiedName ?: "",
    )
}
