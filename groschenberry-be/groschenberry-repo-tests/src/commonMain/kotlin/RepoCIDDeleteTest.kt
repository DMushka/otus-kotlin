package com.otus.otuskotlin.groschenberry.backend.repo.tests

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCID
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId
import com.otus.otuskotlin.groschenberry.common.repo.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull

abstract class RepoCIDDeleteTest {
    abstract val repo: IRepoCI
    protected open val deleteSucc = initObjects[0]
    protected open val notFoundId = GrschbrCIId("cid-repo-delete-notFound")

    @Test
    fun deleteSuccess() = runRepoTest {
        val result = repo.deleteCID(DbCIIdRequest(deleteSucc.id))
        assertIs<DbCIDResponseOk>(result)
        assertEquals(deleteSucc.description, result.data.description)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.readCID(DbCIIdRequest(notFoundId))

        assertIs<DbCIResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertNotNull(error)
    }

    companion object : BaseInitCIDs("delete") {
        override val initObjects: List<GrschbrCID> = listOf(
            createInitTestModel("delete"),
        )
    }
}
