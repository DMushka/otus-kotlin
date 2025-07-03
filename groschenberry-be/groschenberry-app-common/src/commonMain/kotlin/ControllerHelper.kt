package com.otus.otuskotlin.groschenberry.app.common

import kotlinx.datetime.Clock
import com.otus.otuskotlin.groschenberry.api.log.mapper.toLog
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.helpers.asGrschbrError
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCommand
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import kotlin.reflect.KClass

suspend inline fun <T> IGrschbrAppSettings.controllerHelper(
    crossinline getRequest: suspend GrschbrContext.() -> Unit,
    crossinline toResponse: suspend GrschbrContext.() -> T,
    clazz: KClass<*>,
    logId: String,
): T {
    val logger = corSettings.loggerProvider.logger(clazz)
    val ctx = GrschbrContext(
        timeStart = Clock.System.now(),
    )
    return try {
        ctx.getRequest()
        logger.info(
            msg = "Request $logId type ${ctx.type} started for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        processor.exec(ctx)
        logger.info(
            msg = "Request $logId type ${ctx.type} processed for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        ctx.toResponse()
    } catch (e: Throwable) {
        logger.error(
            msg = "Request $logId type ${ctx.type} failed for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        ctx.state = GrschbrState.FAILING
        ctx.errors.add(e.asGrschbrError())
        processor.exec(ctx)
        if (ctx.command == GrschbrCommand.NONE) {
            ctx.command = GrschbrCommand.READ
        }
        ctx.toResponse()
    }
}
