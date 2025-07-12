package com.otus.otuskotlin.groschenberry.biz.validation

import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.groschenberry.biz.GrschbrCIProcessor
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationIssueYearCorrect(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
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
    assertEquals("1960", ctx.cidValidated.issueYear)
}

fun validationIssueYearTrim(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.DETAIL,
        workMode = GrschbrWorkMode.TEST,
        cidRequest = GrschbrCIDStub.get().apply { issueYear = " \n\t 2025 \t\n " }
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(GrschbrState.FAILING, ctx.state)
    assertEquals("2025", ctx.cidValidated.issueYear)
}

fun validationIssueYearNoneNumeric(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.DETAIL,
        workMode = GrschbrWorkMode.TEST,
        cidRequest = GrschbrCIDStub.get().apply { issueYear = "fg12" }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(GrschbrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("issueYear", error?.field)
    assertContains(error?.message ?: "", "numeric")
}

fun validationIssueYearInvalidLength(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.DETAIL,
        workMode = GrschbrWorkMode.TEST,
        cidRequest = GrschbrCIDStub.get().apply { issueYear = "00045" }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(GrschbrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("issueYear", error?.field)
    assertContains(error?.message ?: "", "less")
}

fun validationIssueYearInFuture(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.DETAIL,
        workMode = GrschbrWorkMode.TEST,
        cidRequest = GrschbrCIDStub.get().apply { issueYear = "9999" }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(GrschbrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("issueYear", error?.field)
    assertContains(error?.message ?: "", "future")
}

fun validationIssueYearInPast(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.DETAIL,
        workMode = GrschbrWorkMode.TEST,
        cidRequest = GrschbrCIDStub.get().apply { issueYear = "-999" }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(GrschbrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("issueYear", error?.field)
    assertContains(error?.message ?: "", "earlier")
}