package com.otus.otuskotlin.groschenberry.biz.validation

import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.groschenberry.biz.GrschbrCIProcessor
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationStartYearCorrect(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
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
    assertEquals("1956", ctx.cibValidated.startYear)
}

fun validationStartYearTrim(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.BASIC,
        workMode = GrschbrWorkMode.TEST,
        cibRequest = GrschbrCIBStub.get().apply { startYear = " \n\t 2025 \t\n " }
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(GrschbrState.FAILING, ctx.state)
    assertEquals("2025", ctx.cibValidated.startYear)
}

fun validationStartYearNoneNumeric(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.BASIC,
        workMode = GrschbrWorkMode.TEST,
        cibRequest = GrschbrCIBStub.get().apply { startYear = "fgh2" }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(GrschbrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("startYear", error?.field)
    assertContains(error?.message ?: "", "numeric")
}

fun validationStartYearInvalidLength(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.BASIC,
        workMode = GrschbrWorkMode.TEST,
        cibRequest = GrschbrCIBStub.get().apply { startYear = "00045" }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(GrschbrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("startYear", error?.field)
    assertContains(error?.message ?: "", "less")
}

fun validationStartYearInFuture(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.BASIC,
        workMode = GrschbrWorkMode.TEST,
        cibRequest = GrschbrCIBStub.get().apply { startYear = "9999" }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(GrschbrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("startYear", error?.field)
    assertContains(error?.message ?: "", "future")
}

fun validationStartYearInPast(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.BASIC,
        workMode = GrschbrWorkMode.TEST,
        cibRequest = GrschbrCIBStub.get().apply { startYear = "-999" }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(GrschbrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("startYear", error?.field)
    assertContains(error?.message ?: "", "earlier")
}