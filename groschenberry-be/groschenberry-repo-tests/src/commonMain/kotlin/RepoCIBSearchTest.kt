package com.otus.otuskotlin.groschenberry.backend.repo.tests

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIB
import com.otus.otuskotlin.groschenberry.common.repo.DbCIFilterRequest
import com.otus.otuskotlin.groschenberry.common.repo.DbCIBsResponseOk
import com.otus.otuskotlin.groschenberry.common.repo.IRepoCI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


abstract class RepoCIBSearchTest {
    abstract val repo: IRepoCI

    protected open val initializedObjects: List<GrschbrCIB> = initObjects

    @Test
    fun searchDescription() = runRepoTest {
        val result = repo.searchCIB(DbCIFilterRequest(descriptionFilter = "1"))
        assertIs<DbCIBsResponseOk>(result)
        val expected = listOf(initializedObjects[0], initializedObjects[2]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data.sortedBy { it.id.asString() })
    }

    companion object: BaseInitCIBs("search") {

        override val initObjects: List<GrschbrCIB> = listOf(
            createInitTestModel("cib1"),
            createInitTestModel("cib2", searchValue = "2"),
            createInitTestModel("cib3", searchValue = "1"),
            createInitTestModel("cib4", searchValue = "4"),
            createInitTestModel("cib5", searchValue = "5"),
            )
    }
}
