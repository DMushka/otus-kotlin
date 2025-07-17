package com.otus.otuskotlin.groschenberry.biz.validation

import com.otus.otuskotlin.groschenberry.api.log.mapper.toLog
import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIFilter
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.cor.rootChain
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidateSearchStringLengthTest {
    @Test
    fun emptyString() = runTest {
        val ctx = GrschbrContext(
            state = GrschbrState.RUNNING,
            type = GrschbrType.BASIC,
            ciFilterValidated = GrschbrCIFilter(searchString = "")
        )
        chain.exec(ctx)
        assertEquals(GrschbrState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun blankString() = runTest {
        val ctx = GrschbrContext(
            state = GrschbrState.RUNNING,
            type = GrschbrType.BASIC,
            ciFilterValidated = GrschbrCIFilter(searchString = "   "))
        ctx.logger.log(data = ctx.toLog("blankString"))
        chain.exec(ctx)
        ctx.logger.log(data = ctx.toLog("blankString"))
        assertEquals(GrschbrState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun shortString() = runTest {
        val ctx = GrschbrContext(
            state = GrschbrState.RUNNING,
            type = GrschbrType.BASIC,
            ciFilterValidated = GrschbrCIFilter(searchString = "12")
        )
        chain.exec(ctx)
        assertEquals(GrschbrState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-searchString-tooShort", ctx.errors.first().code)
    }

    @Test
    fun normalString() = runTest {
        val ctx = GrschbrContext(
            state = GrschbrState.RUNNING,
            type = GrschbrType.BASIC,
            ciFilterValidated = GrschbrCIFilter(searchString = "123")
        )
        chain.exec(ctx)
        assertEquals(GrschbrState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun longString() = runTest {
        val ctx = GrschbrContext(
            state = GrschbrState.RUNNING,
            type = GrschbrType.DETAIL,
            ciFilterValidated = GrschbrCIFilter(searchString = "12".repeat(51))
        )
        chain.exec(ctx)
        assertEquals(GrschbrState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-searchString-tooLong", ctx.errors.first().code)
    }

    companion object {
        val chain = rootChain {
            validateSearchStringLength("")
        }.build()
    }
}
