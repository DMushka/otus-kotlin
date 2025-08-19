package com.otus.otuskotlin.groschenberry.backend.repo.tests

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIB
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId
import com.otus.otuskotlin.groschenberry.common.repo.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull

abstract class RepoCIBDeleteTest {
    abstract val repo: IRepoCI
    protected open val deleteSucc = initObjects[0]
    protected open val deleteConc = initObjects[1]
    protected open val notFoundId = GrschbrCIId("cib-repo-delete-notFound")

    @Test
    fun deleteSuccess() = runRepoTest {
        val lockOld = deleteSucc.lock
        val result = repo.deleteCIB(DbCIIdRequest(deleteSucc.id, lock = lockOld))
        assertIs<DbCIBResponseOk>(result)
        assertEquals(deleteSucc.title, result.data.title)
        assertEquals(deleteSucc.description, result.data.description)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.deleteCIB(DbCIIdRequest(notFoundId, lockOld))

        assertIs<DbCIResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertNotNull(error)
    }

    @Test
    fun deleteConcurrency() = runRepoTest {
        val result = repo.deleteCIB(DbCIIdRequest(deleteConc.id, lock = lockBad))

        assertIs<DbCIBResponseErrWithData>(result)
        val error = result.errors.find { it.code == "repo-cib-concurrency" }
        assertNotNull(error)
    }

    companion object : BaseInitCIBs("delete") {
        override val initObjects: List<GrschbrCIB> = listOf(
            createInitTestModel("delete"),
            createInitTestModel("deleteConc"),
        )
    }
}
