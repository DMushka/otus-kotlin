package com.otus.otuskotlin.groschenberry.cor

import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.groschenberry.cor.handlers.CorChain
import com.otus.otuskotlin.groschenberry.cor.handlers.CorWorker
import kotlin.test.Test
import kotlin.test.assertEquals

class CorChainTest {
    @Test
    fun `chain should execute workers`() = runTest {
        val createWorker = { title: String ->
            CorWorker<TestContext>(
                title = title,
                blockOn = { status == TestContext.CorStatuses.NONE },
                blockHandle = { history += "$title; " }
            )
        }
        val chain = CorChain<TestContext>(
            execs = listOf(createWorker("w1"), createWorker("w2")),
            title = "chain",
        )
        val ctx = TestContext()
        chain.exec(ctx)
        assertEquals("w1; w2; ", ctx.history)
    }
}
