package com.otus.otuskotlin.groschenberry.biz.repo

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoUpdateTest : RepoBaseTest()  {

    private val command = GrschbrCommand.UPDATE

    @Test
    fun repoUpdateCIBSuccessTest() = runTest {
        val cibToUpdate = GrschbrCIBStub.get().apply {
            id = GrschbrCIId("123")
            title = "xyz"
            description = "xyz"
            lock = GrschbrCILock("123")
        }
        val ctx = GrschbrContext(
            command = command,
            state = GrschbrState.NONE,
            type = GrschbrType.BASIC,
            workMode = GrschbrWorkMode.TEST,
            cibRequest = cibToUpdate,
        )
        processor.exec(ctx)
        assertEquals(GrschbrState.FINISHED, ctx.state)
        assertEquals(cibToUpdate.id, ctx.cibResponse.id)
        assertEquals(cibToUpdate.title, ctx.cibResponse.title)
        assertEquals(cibToUpdate.description, ctx.cibResponse.description)
    }

    @Test
    fun repoUpdateCIDSuccessTest() = runTest {
        val cidToUpdate = GrschbrCIDStub.get().apply {
            id = GrschbrCIId("123")
            description = "xyz"
            lock = GrschbrCILock("123")
        }
        val ctx = GrschbrContext(
            command = command,
            state = GrschbrState.NONE,
            type = GrschbrType.DETAIL,
            workMode = GrschbrWorkMode.TEST,
            cidRequest = cidToUpdate,
        )
        processor.exec(ctx)
        assertEquals(GrschbrState.FINISHED, ctx.state)
        assertEquals(cidToUpdate.id, ctx.cidResponse.id)
        assertEquals(cidToUpdate.description, ctx.cidResponse.description)
    }

    @Test
    fun repoUpdateNotFoundTest() = repoNotFoundTest(command)
}
