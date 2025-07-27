package com.otus.otuskotlin.groschenberry.backend.repo.tests

import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.common.repo.DbCIDRequest
import com.otus.otuskotlin.groschenberry.common.repo.DbCIDResponseOk
import com.otus.otuskotlin.groschenberry.repo.common.IRepoCIInitializable
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub
import kotlin.test.*


abstract class RepoCIDCreateTest {
    abstract val repo: IRepoCIInitializable
    protected open val uuidNew = GrschbrCIId("90000000-0000-0000-0000-000000000001")

    private val createObj = GrschbrCIDStub.get().apply {
        description = "create object description"
        id = GrschbrCIId.NONE
    }

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createCID(DbCIDRequest(createObj))
        val expected = createObj
        assertIs<DbCIDResponseOk>(result)
        assertEquals(uuidNew, result.data.id)
        assertEquals(expected.description, result.data.description)
        assertNotEquals(GrschbrCIId.NONE, result.data.id)
    }

    companion object : BaseInitCIDs("create") {
        override val initObjects: List<GrschbrCID> = emptyList()
    }
}
