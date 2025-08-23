package com.otus.otuskotlin.groschenberry.biz.repo

import com.otus.otuskotlin.groschenberry.biz.addTestPrincipal
import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizRepoCreateTest : RepoBaseTest() {

    private val command = GrschbrCommand.CREATE
        @Test
    fun repoCreateCIBSuccessTest() = runTest {
        val ctx = GrschbrContext(
            command = command,
            state = GrschbrState.NONE,
            type = GrschbrType.BASIC,
            workMode = GrschbrWorkMode.TEST,
            cibRequest = GrschbrCIBStub.get().apply {
                title = "abc"
                description = "abc"
            },
        )
        ctx.addTestPrincipal()
        processor.exec(ctx)
        assertEquals(GrschbrState.FINISHED, ctx.state)
        assertNotEquals(GrschbrCIId.NONE, ctx.cibResponse.id)
        assertEquals("abc", ctx.cibResponse.title)
        assertEquals("abc", ctx.cibResponse.description)
    }

    @Test
    fun repoCreateCIDSuccessTest() = runTest {
        val ctx = GrschbrContext(
            command = command,
            state = GrschbrState.NONE,
            type = GrschbrType.DETAIL,
            workMode = GrschbrWorkMode.TEST,
            cidRequest = GrschbrCIDStub.get().apply {
                description = "abc"
            },
        )
        ctx.addTestPrincipal()
        processor.exec(ctx)
        assertEquals(GrschbrState.FINISHED, ctx.state)
        assertNotEquals(GrschbrCIId.NONE, ctx.cidResponse.id)
        assertEquals("abc", ctx.cidResponse.description)
    }
}
