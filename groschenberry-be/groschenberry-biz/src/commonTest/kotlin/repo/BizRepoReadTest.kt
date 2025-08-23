package com.otus.otuskotlin.groschenberry.biz.repo

import com.otus.otuskotlin.groschenberry.biz.addTestPrincipal
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoReadTest : RepoBaseTest() {

    private val command = GrschbrCommand.READ

    @Test
    fun repoReadCIBSuccessTest() = runTest {
        val ctx = GrschbrContext(
            command = command,
            state = GrschbrState.NONE,
            type = GrschbrType.BASIC,
            workMode = GrschbrWorkMode.TEST,
            cibRequest = GrschbrCIBStub.get().apply {
                id = GrschbrCIId("123")
            },
        )
        ctx.addTestPrincipal()
        processor.exec(ctx)
        assertEquals(GrschbrState.FINISHED, ctx.state)
        assertEquals(initCIB.id, ctx.cibResponse.id)
        assertEquals(initCIB.title, ctx.cibResponse.title)
        assertEquals(initCIB.description, ctx.cibResponse.description)
    }

    @Test
    fun repoReadCIDSuccessTest() = runTest {
        val ctx = GrschbrContext(
            command = command,
            state = GrschbrState.NONE,
            type = GrschbrType.DETAIL,
            workMode = GrschbrWorkMode.TEST,
            cidRequest = GrschbrCIDStub.get().apply {
                id = GrschbrCIId("123")
            },
        )
        ctx.addTestPrincipal()
        processor.exec(ctx)
        assertEquals(GrschbrState.FINISHED, ctx.state)
        assertEquals(initCID.id, ctx.cidResponse.id)
        assertEquals(initCID.description, ctx.cidResponse.description)
    }

    @Test
    fun repoReadNotFoundTest() = repoNotFoundTest(command)
}
