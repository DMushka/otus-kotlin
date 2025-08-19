package com.otus.otuskotlin.groschenberry.biz.repo

import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BizRepoDeleteTest : RepoBaseTest() {

    private val command = GrschbrCommand.DELETE

    @Test
    fun repoDeleteCIBSuccessTest() = runTest {
        val cibToDelete = GrschbrCIBStub.get ().apply {
            id = GrschbrCIId("123")
        }
        val ctx = GrschbrContext(
            command = command,
            state = GrschbrState.NONE,
            type = GrschbrType.BASIC,
            workMode = GrschbrWorkMode.TEST,
            cibRequest = cibToDelete,
        )
        processor.exec(ctx)
        assertEquals(GrschbrState.FINISHED, ctx.state)
        assertTrue { ctx.errors.isEmpty() }
        assertEquals(initCIB.id, ctx.cibResponse.id)
        assertEquals(initCIB.description, ctx.cibResponse.description)
    }

    @Test
    fun repoDeleteCIDSuccessTest() = runTest {
        val cidToDelete = GrschbrCIDStub.get().apply {
            id = GrschbrCIId("123")
        }
        val ctx = GrschbrContext(
            command = command,
            state = GrschbrState.NONE,
            type = GrschbrType.DETAIL,
            workMode = GrschbrWorkMode.TEST,
            cidRequest = cidToDelete,
        )
        processor.exec(ctx)
        assertEquals(GrschbrState.FINISHED, ctx.state)
        assertTrue { ctx.errors.isEmpty() }
        assertEquals(initCID.id, ctx.cidResponse.id)
        assertEquals(initCID.description, ctx.cidResponse.description)
    }

    @Test
    fun repoDeleteNotFoundTest() = repoNotFoundTest(command)
}
