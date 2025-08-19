package com.otus.otuskotlin.groschenberry.biz.repo

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.*
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoSearchTest : RepoBaseTest()  {

    private val command = GrschbrCommand.SEARCH

    @Test
    fun repoSearchCIBSuccessTest() = runTest {
        val ctx = GrschbrContext(
            command = command,
            state = GrschbrState.NONE,
            type = GrschbrType.BASIC,
            workMode = GrschbrWorkMode.TEST,
            ciFilterRequest = GrschbrCIFilter(
                searchString = "abc",
            ),
        )
        processor.exec(ctx)
        assertEquals(GrschbrState.FINISHED, ctx.state)
        assertEquals(1, ctx.cibsResponse.size)
    }

    @Test
    fun repoSearchCIDSuccessTest() = runTest {
        val ctx = GrschbrContext(
            command = command,
            state = GrschbrState.NONE,
            type = GrschbrType.DETAIL,
            workMode = GrschbrWorkMode.TEST,
            ciFilterRequest = GrschbrCIFilter(
                searchString = "abc",
            ),
        )
        processor.exec(ctx)
        assertEquals(GrschbrState.FINISHED, ctx.state)
        assertEquals(1, ctx.cidsResponse.size)
    }
}
