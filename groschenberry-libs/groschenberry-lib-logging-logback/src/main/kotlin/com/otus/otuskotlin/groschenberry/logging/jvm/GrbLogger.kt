package com.otus.otuskotlin.groschenberry.logging.jvm

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import com.otus.otuskotlin.groschenberry.logging.common.IGrbLogWrapper
import kotlin.reflect.KClass

/**
 * Generate internal GrbLogContext logger
 *
 * @param logger Logback instance from [LoggerFactory.getLogger()]
 */
fun grbLoggerLogback(logger: Logger): IGrbLogWrapper = GrbLogWrapperLogback(
    logger = logger,
    loggerId = logger.name,
)

fun grbLoggerLogback(clazz: KClass<*>): IGrbLogWrapper = grbLoggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)
@Suppress("unused")
fun grbLoggerLogback(loggerId: String): IGrbLogWrapper = grbLoggerLogback(LoggerFactory.getLogger(loggerId) as Logger)
