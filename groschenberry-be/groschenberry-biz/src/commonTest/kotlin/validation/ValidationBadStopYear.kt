package com.otus.otuskotlin.groschenberry.biz.validation

import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.groschenberry.biz.GrschbrCIProcessor
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationStopYearCorrect(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.BASIC,
        workMode = GrschbrWorkMode.TEST,
        cibRequest = GrschbrCIBStub.get(),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(GrschbrState.FAILING, ctx.state)
    assertEquals("1961", ctx.cibValidated.stopYear)
}

fun validationStopYearTrim(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.BASIC,
        workMode = GrschbrWorkMode.TEST,
        cibRequest = GrschbrCIBStub.get().apply { stopYear = " \n\t 2025 \t\n " }
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(GrschbrState.FAILING, ctx.state)
    assertEquals("2025", ctx.cibValidated.stopYear)
}

fun validationStopYearNoneNumeric(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.BASIC,
        workMode = GrschbrWorkMode.TEST,
        cibRequest = GrschbrCIBStub.get().apply { stopYear = "gh12" }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(GrschbrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("stopYear", error?.field)
    assertContains(error?.message ?: "", "numeric")
}

fun validationStopYearInvalidLength(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.BASIC,
        workMode = GrschbrWorkMode.TEST,
        cibRequest = GrschbrCIBStub.get().apply { stopYear = "12345" }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(GrschbrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("stopYear", error?.field)
    assertContains(error?.message ?: "", "less")
}

fun validationStopYearInPast(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.BASIC,
        workMode = GrschbrWorkMode.TEST,
        cibRequest = GrschbrCIBStub.get().apply { stopYear = "-999" }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(GrschbrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("stopYear", error?.field)
    assertContains(error?.message ?: "", "earlier")
}