package com.otus.otuskotlin.groschenberry.biz.repo

import com.otus.otuskotlin.groschenberry.backend.repo.tests.CIRepositoryMock
import com.otus.otuskotlin.groschenberry.biz.GrschbrCIProcessor
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.GrschbrCorSettings
import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.common.repo.DbCIBResponseOk
import com.otus.otuskotlin.groschenberry.common.repo.DbCIBsResponseOk
import com.otus.otuskotlin.groschenberry.common.repo.DbCIDResponseOk
import com.otus.otuskotlin.groschenberry.common.repo.DbCIDsResponseOk
import com.otus.otuskotlin.groschenberry.common.repo.DbCIResponseErr
import com.otus.otuskotlin.groschenberry.common.repo.errorNotFound
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

open class RepoBaseTest {
    val uuid = "10000000-0000-0000-0000-000000000001"
    val initCIB = GrschbrCIBStub.get().apply {
        id = GrschbrCIId("123")
        title = "abc"
        description = "abc"
    }
    val initCID = GrschbrCIDStub.get().apply {
        id = GrschbrCIId("123")
        description = "abc"
    }
    val repo = CIRepositoryMock(
        invokeCreateCIB = {
            DbCIBResponseOk(
                data = GrschbrCIBStub.get().apply {
                    id = GrschbrCIId(uuid)
                    title = it.cib.title
                    description = it.cib.description
                }
            )
        },
        invokeCreateCID = {
            DbCIDResponseOk(
                data = GrschbrCIDStub.get().apply {
                    id = GrschbrCIId(uuid)
                    description = it.cid.description
                }
            )
        },
        invokeReadCIB = {
            if (it.id == initCIB.id) {
                DbCIBResponseOk(
                    data = initCIB,
                )
            } else errorNotFound(it.id)
        },
        invokeReadCID = {
            DbCIDResponseOk(
                data = initCID,
            )
        },
        invokeDeleteCIB = {
            if (it.id == initCIB.id)
                DbCIBResponseOk(
                    data = initCIB
                )
            else DbCIResponseErr()
        },
        invokeDeleteCID = {
            if (it.id == initCID.id)
                DbCIDResponseOk(
                    data = initCID
                )
            else DbCIResponseErr()
        },
        invokeUpdateCIB = {
            DbCIBResponseOk(
                data = GrschbrCIBStub.get().apply {
                    id = GrschbrCIId("123")
                    title = "xyz"
                    description = "xyz"
                }
            )
        },
        invokeUpdateCID = {
            DbCIDResponseOk(
                data = GrschbrCIDStub.get().apply {
                    id = GrschbrCIId("123")
                    description = "xyz"
                }
            )
        },
        invokeSearchCIB = {
            DbCIBsResponseOk(
                data = listOf(initCIB),
            )
        },
        invokeSearchCID = {
            DbCIDsResponseOk(
                data = listOf(initCID),
            )
        }
    )
    val settings = GrschbrCorSettings(repoTest = repo)
    val processor = GrschbrCIProcessor(settings)

    fun repoNotFoundTest(command: GrschbrCommand) = runTest {
        val ctx = GrschbrContext(
            command = command,
            state = GrschbrState.NONE,
            type = GrschbrType.BASIC,
            workMode = GrschbrWorkMode.TEST,
            cibRequest = GrschbrCIBStub.get().apply {
                id = GrschbrCIId("12345")
                title = "xyz"
                description = "xyz"
                lock = GrschbrCILock("123")
            },
        )
        processor.exec(ctx)
        assertEquals(GrschbrState.FAILING, ctx.state)
        assertEquals(GrschbrCIB(), ctx.cibResponse)
        assertEquals(1, ctx.errors.size)
        assertNotNull(ctx.errors.find { it.code == "repo-not-found" }, "Errors must contain not-found")
    }
}