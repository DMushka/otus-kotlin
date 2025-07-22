package com.otus.otuskotlin.groschenberry.backend.repo.tests

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIB
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId
import com.otus.otuskotlin.groschenberry.common.repo.DbCIBResponseOk
import com.otus.otuskotlin.groschenberry.common.repo.DbCIIdRequest
import com.otus.otuskotlin.groschenberry.common.repo.DbCIResponseErr
import com.otus.otuskotlin.groschenberry.common.repo.DbCIsResponseErr
import com.otus.otuskotlin.groschenberry.common.repo.IRepoCI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


abstract class RepoCIBReadTest {
    abstract val repo: IRepoCI
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readCIB(DbCIIdRequest(readSucc.id))
        assertIs<DbCIBResponseOk>(result)
        assertEquals(readSucc, result.data)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readCIB(DbCIIdRequest(notFoundId))

        assertIs<DbCIResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitCIBs("read") {
        override val initObjects: List<GrschbrCIB> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = GrschbrCIId("cib-repo-read-notFound")

    }
}
