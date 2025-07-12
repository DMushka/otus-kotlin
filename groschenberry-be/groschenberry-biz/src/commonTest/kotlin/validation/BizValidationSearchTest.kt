package com.otus.otuskotlin.groschenberry.biz.validation

import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIFilter
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCommand
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.models.GrschbrWorkMode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizValidationSearchTest: BaseBizValidationTest() {
    override val command = GrschbrCommand.SEARCH

    @Test
    fun correctEmpty() = runTest {
        val ctx = GrschbrContext(
            command = command,
            state = GrschbrState.NONE,
            workMode = GrschbrWorkMode.TEST,
            ciFilterRequest = GrschbrCIFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(GrschbrState.FAILING, ctx.state)
    }
}
