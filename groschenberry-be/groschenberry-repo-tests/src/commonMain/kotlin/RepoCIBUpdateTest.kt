package com.otus.otuskotlin.groschenberry.backend.repo.tests

import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.common.repo.*
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


abstract class RepoCIBUpdateTest {
    abstract val repo: IRepoCI
    protected open val updateSucc = initObjects[0]
    protected val updateIdNotFound = GrschbrCIId("cib-repo-update-not-found")

    private val reqUpdateSucc by lazy {
        GrschbrCIBStub.get().apply {
            id = updateSucc.id
            title = "update object"
            description = "update object description"
        }
    }
    private val reqUpdateNotFound = GrschbrCIBStub.get().apply {
        id = updateIdNotFound
        title = "update object not found"
        description = "update object not found description"
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateCIB(DbCIBRequest(reqUpdateSucc))
        assertIs<DbCIBResponseOk>(result)
        assertEquals(reqUpdateSucc.id, result.data.id)
        assertEquals(reqUpdateSucc.title, result.data.title)
        assertEquals(reqUpdateSucc.description, result.data.description)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateCIB(DbCIBRequest(reqUpdateNotFound))
        assertIs<DbCIResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitCIBs("update") {
        override val initObjects: List<GrschbrCIB> = listOf(
            createInitTestModel("update"),
        )
    }
}
