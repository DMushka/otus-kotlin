package com.otus.otuskotlin.groschenberry.biz.validation

import com.otus.otuskotlin.groschenberry.api.log.mapper.toLog
import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIB
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIFilter
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.cor.rootChain
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidateTitleTest {
    @Test
    fun emptyString() = runTest {
        val ctx = GrschbrContext(
            state = GrschbrState.RUNNING,
            type = GrschbrType.BASIC,
            cibValidated = GrschbrCIB(title = "")
        )
        chain.exec(ctx)
        assertEquals(GrschbrState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-title-empty", ctx.errors.first().code)
    }

    @Test
    fun noContent() = runTest {
        val ctx = GrschbrContext(
            state = GrschbrState.RUNNING,
            type = GrschbrType.BASIC,
            cibValidated = GrschbrCIB(title = "12!@#$%^&*()_+-=")
        )
        chain.exec(ctx)
        assertEquals(GrschbrState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-title-noContent", ctx.errors.first().code)
    }

    @Test
    fun normalString() = runTest {
        val ctx = GrschbrContext(
            state = GrschbrState.RUNNING,
            type = GrschbrType.BASIC,
            cibValidated = GrschbrCIB(title = "R")
        )
        chain.exec(ctx)
        ctx.logger.log(data = ctx.toLog("normalString"))
        assertEquals(GrschbrState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    companion object {
        val chain = rootChain<GrschbrContext> {
            validateTitle("Валидация title", REG_EXP_ID)
        }.build()
    }
}
