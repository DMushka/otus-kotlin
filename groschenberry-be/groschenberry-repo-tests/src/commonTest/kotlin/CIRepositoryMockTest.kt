package com.otus.otuskotlin.groschenberry.backend.repo.tests

import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIB
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCID
import com.otus.otuskotlin.groschenberry.common.repo.*
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class CIRepositoryMockTest {
    private val repo = CIRepositoryMock(
        invokeCreateCIB = { DbCIBResponseOk(GrschbrCIBStub.prepareResult { title = "create" }) },
        invokeReadCIB = { DbCIBResponseOk(GrschbrCIBStub.prepareResult { title = "read" }) },
        invokeUpdateCIB = { DbCIBResponseOk(GrschbrCIBStub.prepareResult { title = "update" }) },
        invokeDeleteCIB = { DbCIBResponseOk(GrschbrCIBStub.prepareResult { title = "delete" }) },
        invokeSearchCIB = { DbCIBsResponseOk(listOf(GrschbrCIBStub.prepareResult { title = "search" })) },
        invokeCreateCID = { DbCIDResponseOk(GrschbrCIDStub.prepareResult { description = "create" }) },
        invokeReadCID = { DbCIDResponseOk(GrschbrCIDStub.prepareResult { description = "read" }) },
        invokeUpdateCID = { DbCIDResponseOk(GrschbrCIDStub.prepareResult { description = "update" }) },
        invokeDeleteCID = { DbCIDResponseOk(GrschbrCIDStub.prepareResult { description = "delete" }) },
        invokeSearchCID = { DbCIDsResponseOk(listOf(GrschbrCIDStub.prepareResult { description = "search" })) },

        )

    @Test
    fun mockCreateCIB() = runTest {
        val result = repo.createCIB(DbCIBRequest(GrschbrCIB()))
        assertIs<DbCIBResponseOk>(result)
        assertEquals("create", result.data.title)
    }

    @Test
    fun mockReadCIB() = runTest {
        val result = repo.readCIB(DbCIIdRequest(GrschbrCIB()))
        assertIs<DbCIBResponseOk>(result)
        assertEquals("read", result.data.title)
    }

    @Test
    fun mockUpdateCIB() = runTest {
        val result = repo.updateCIB(DbCIBRequest(GrschbrCIB()))
        assertIs<DbCIBResponseOk>(result)
        assertEquals("update", result.data.title)
    }

    @Test
    fun mockDeleteCIB() = runTest {
        val result = repo.deleteCIB(DbCIIdRequest(GrschbrCIB()))
        assertIs<DbCIBResponseOk>(result)
        assertEquals("delete", result.data.title)
    }

    @Test
    fun mockSearchCIB() = runTest {
        val result = repo.searchCIB(DbCIFilterRequest())
        assertIs<DbCIBsResponseOk>(result)
        assertEquals("search", result.data.first().title)
    }

    @Test
    fun mockCreateCID() = runTest {
        val result = repo.createCID(DbCIDRequest(GrschbrCID()))
        assertIs<DbCIDResponseOk>(result)
        assertEquals("create", result.data.description)
    }

    @Test
    fun mockReadCID() = runTest {
        val result = repo.readCID(DbCIIdRequest(GrschbrCID()))
        assertIs<DbCIDResponseOk>(result)
        assertEquals("read", result.data.description)
    }

    @Test
    fun mockUpdateCID() = runTest {
        val result = repo.updateCID(DbCIDRequest(GrschbrCID()))
        assertIs<DbCIDResponseOk>(result)
        assertEquals("update", result.data.description)
    }

    @Test
    fun mockDeleteCID() = runTest {
        val result = repo.deleteCID(DbCIIdRequest(GrschbrCID()))
        assertIs<DbCIDResponseOk>(result)
        assertEquals("delete", result.data.description)
    }

    @Test
    fun mockSearchCID() = runTest {
        val result = repo.searchCID(DbCIFilterRequest())
        assertIs<DbCIDsResponseOk>(result)
        assertEquals("search", result.data.first().description)
    }
}
