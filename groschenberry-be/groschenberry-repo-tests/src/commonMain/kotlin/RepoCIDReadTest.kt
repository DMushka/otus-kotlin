package com.otus.otuskotlin.groschenberry.backend.repo.tests

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCID
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId
import com.otus.otuskotlin.groschenberry.common.repo.DbCIDResponseOk
import com.otus.otuskotlin.groschenberry.common.repo.DbCIIdRequest
import com.otus.otuskotlin.groschenberry.common.repo.DbCIResponseErr
import com.otus.otuskotlin.groschenberry.common.repo.IRepoCI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


abstract class RepoCIDReadTest {
    abstract val repo: IRepoCI
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readCID(DbCIIdRequest(readSucc.id))

        assertIs<DbCIDResponseOk>(result)
        assertEquals(readSucc, result.data)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readCID(DbCIIdRequest(notFoundId))

        assertIs<DbCIResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitCIDs("read") {
        override val initObjects: List<GrschbrCID> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = GrschbrCIId("cid-repo-read-notFound")

    }
}
