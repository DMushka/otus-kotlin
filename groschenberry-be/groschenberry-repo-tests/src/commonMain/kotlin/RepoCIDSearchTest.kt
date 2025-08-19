package com.otus.otuskotlin.groschenberry.backend.repo.tests

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCID
import com.otus.otuskotlin.groschenberry.common.repo.DbCIFilterRequest
import com.otus.otuskotlin.groschenberry.common.repo.DbCIDsResponseOk
import com.otus.otuskotlin.groschenberry.common.repo.IRepoCI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.text.clear


abstract class RepoCIDSearchTest {
    abstract val repo: IRepoCI

    protected open val initializedObjects: List<GrschbrCID> = initObjects

    @Test
    fun searchDescription() = runRepoTest {
        val result = repo.searchCID(DbCIFilterRequest(descriptionFilter = "1"))
        assertIs<DbCIDsResponseOk>(result)
        val expected = listOf(initializedObjects[0], initializedObjects[2]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data.sortedBy { it.id.asString() })
    }

    companion object: BaseInitCIDs("search") {

        override val initObjects: List<GrschbrCID> = listOf(
            createInitTestModel("cid1"),
            createInitTestModel("cid2", description = "2"),
            createInitTestModel("cid3", description = "1"),
            createInitTestModel("cid4", description = "4"),
            createInitTestModel("cid5", description = "5"),
        )
    }
}
