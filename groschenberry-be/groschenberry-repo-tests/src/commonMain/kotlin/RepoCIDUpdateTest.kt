package com.otus.otuskotlin.groschenberry.backend.repo.tests

import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.common.repo.*
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

abstract class RepoCIDUpdateTest {
    abstract val repo: IRepoCI
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateIdNotFound = GrschbrCIId("cid-repo-update-not-found")
    protected val lockBad = GrschbrCILock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = GrschbrCILock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        GrschbrCIDStub.get().apply {
            id = updateSucc.id
            description = "update object description"
            lock = initObjects.first().lock
        }
    }
    private val reqUpdateNotFound = GrschbrCIDStub.get().apply {
        id = updateIdNotFound
        description = "update object not found description"
        lock = initObjects.first().lock
    }

    private val reqUpdateConc by lazy {
        GrschbrCIDStub.get().apply {
            id = updateConc.id
            description = "update object not found description"
            lock = lockBad
        }
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateCID(DbCIDRequest(reqUpdateSucc))
        assertIs<DbCIDResponseOk>(result)
        assertEquals(reqUpdateSucc.id, result.data.id)
        assertEquals(reqUpdateSucc.description, result.data.description)
        assertEquals(lockNew, result.data.lock)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateCID(DbCIDRequest(reqUpdateNotFound))
        assertIs<DbCIResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateCID(DbCIDRequest(reqUpdateConc))
        assertIs<DbCIDResponseErrWithData>(result)
        val error = result.errors.find { it.code == "repo-cid-concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitCIDs("update") {
        override val initObjects: List<GrschbrCID> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
