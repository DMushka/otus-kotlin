package com.otus.otuskotlin.groschenberry.biz.validation

import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.groschenberry.biz.GrschbrCIProcessor
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationDescriptionCorrect(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.BASIC,
        workMode = GrschbrWorkMode.TEST,
        cibRequest = GrschbrCIBStub.get()
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(GrschbrState.FAILING, ctx.state)
    assertEquals("Монета стандартная круглая", ctx.cibValidated.description)
}

fun validationDescriptionTrim(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.DETAIL,
        workMode = GrschbrWorkMode.TEST,
        cidRequest = GrschbrCIDStub.get().apply { description = " \n\t   abc \n\t" }
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(GrschbrState.FAILING, ctx.state)
    assertEquals("abc", ctx.cidValidated.description)
}

fun validationDescriptionEmpty(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.BASIC,
        workMode = GrschbrWorkMode.TEST,
        cibRequest =  GrschbrCIBStub.get().apply { description = "" }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(GrschbrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}

fun validationDescriptionSymbols(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.DETAIL,
        workMode = GrschbrWorkMode.TEST,
        cidRequest =  GrschbrCIDStub.get().apply { description = "!@#$%^&*(),.{}" }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(GrschbrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}
