package validation

import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.groschenberry.biz.GrschbrCIProcessor
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationLockCorrect(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.BASIC,
        workMode = GrschbrWorkMode.TEST,
        cibRequest = GrschbrCIBStub.get().apply { lock = GrschbrCILock("123-234-abc-ABC") }
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(GrschbrState.FAILING, ctx.state)
    assertEquals("123-234-abc-ABC", ctx.cibValidated.lock.asString())
}

fun validationLockTrim(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.DETAIL,
        workMode = GrschbrWorkMode.TEST,
        cidRequest = GrschbrCIDStub.get().apply { lock = GrschbrCILock(" \n\t 123-234-abc-ABC \n\t ") }
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(GrschbrState.FAILING, ctx.state)
    assertEquals("123-234-abc-ABC", ctx.cidValidated.lock.asString())
}

fun validationLockEmpty(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.BASIC,
        workMode = GrschbrWorkMode.TEST,
        cibRequest = GrschbrCIBStub.get().apply { lock = GrschbrCILock("") }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(GrschbrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("lock", error?.field)
    assertContains(error?.message ?: "", "id")
}

fun validationLockFormat(command: GrschbrCommand, processor: GrschbrCIProcessor) = runTest {
    val ctx = GrschbrContext(
        command = command,
        state = GrschbrState.NONE,
        type = GrschbrType.DETAIL,
        workMode = GrschbrWorkMode.TEST,
        cidRequest = GrschbrCIDStub.get().apply { lock = GrschbrCILock("!@#\$%^&*(),.{}") }
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(GrschbrState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("lock", error?.field)
    assertContains(error?.message ?: "", "id")
}
