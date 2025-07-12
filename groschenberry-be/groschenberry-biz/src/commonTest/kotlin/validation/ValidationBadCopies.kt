package com.otus.otuskotlin.groschenberry.biz.validation

import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.groschenberry.biz.GrschbrCIProcessor
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationCopiesCorrect(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.DETAIL,
        workMode = GrschbrWorkMode.TEST,
        cidRequest = GrschbrCIDStub.get(),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(GrschbrState.FAILING, ctx.state)
    assertEquals(100, ctx.cidValidated.copies)
}

fun validationCopiesNegative(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.DETAIL,
        workMode = GrschbrWorkMode.TEST,
        cidRequest = GrschbrCIDStub.get().apply { copies = -5 }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(GrschbrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("copies", error?.field)
    assertContains(error?.message ?: "", "positive")
}

fun validationCopiesTooBig(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.DETAIL,
        workMode = GrschbrWorkMode.TEST,
        cidRequest = GrschbrCIDStub.get().apply { copies = 1000000000 }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(GrschbrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("copies", error?.field)
    assertContains(error?.message ?: "", "less")
}
