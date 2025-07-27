package com.otus.otuskotlin.groschenberry.backend.repo.tests

import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.common.repo.DbCIBRequest
import com.otus.otuskotlin.groschenberry.common.repo.DbCIBResponseOk
import com.otus.otuskotlin.groschenberry.repo.common.IRepoCIInitializable
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import kotlin.test.*


abstract class RepoCIBCreateTest {
    abstract val repo: IRepoCIInitializable
    protected open val uuidNew = GrschbrCIId("10000000-0000-0000-0000-000000000001")

    private val createObj = GrschbrCIBStub.get().apply {
        title = "create object"
        description = "create object description"
        id = GrschbrCIId.NONE
    }

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createCIB(DbCIBRequest(createObj))
        val expected = createObj
        assertIs<DbCIBResponseOk>(result)
        assertEquals(uuidNew, result.data.id)
        assertEquals(expected.title, result.data.title)
        assertEquals(expected.description, result.data.description)
        assertNotEquals(GrschbrCIId.NONE, result.data.id)
    }

    companion object : BaseInitCIBs("create") {
        override val initObjects: List<GrschbrCIB> = emptyList()
    }
}
