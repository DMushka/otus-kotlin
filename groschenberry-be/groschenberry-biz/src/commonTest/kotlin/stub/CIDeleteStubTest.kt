package com.otus.otuskotlin.groschenberry.biz.stub

import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.groschenberry.biz.GrschbrCIProcessor
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.common.models.models.GrschbrType
import com.otus.otuskotlin.groschenberry.common.stubs.GrschbrStubs
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub
import kotlin.test.Test
import kotlin.test.assertEquals

class CIDeleteStubTest {

    private val processor = GrschbrCIProcessor()
    val cib = GrschbrCIB(
        id = GrschbrCIId("555"),
    )
    val cid = GrschbrCID(
        id = GrschbrCIId("777"),
    )

    val stubCIB = GrschbrCIBStub.get()
    val stubCID = GrschbrCIDStub.get()

    @Test
    fun deleteCIB() = runTest {

        val ctx = GrschbrContext(
            command = GrschbrCommand.DELETE,
            state = GrschbrState.NONE,
            type = GrschbrType.BASIC,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.SUCCESS,
            cibRequest = cib,
        )
        processor.exec(ctx)

        assertEquals(cib.id, ctx.cibResponse.id)
        assertEquals(stubCIB.title, ctx.cibResponse.title)
        assertEquals(stubCIB.description, ctx.cibResponse.description)
        assertEquals(stubCIB.country, ctx.cibResponse.country)
        assertEquals(stubCIB.currency, ctx.cibResponse.currency)
        assertEquals(stubCIB.material, ctx.cibResponse.material)
        assertEquals(stubCIB.nominal, ctx.cibResponse.nominal)
        assertEquals(stubCIB.diameter, ctx.cibResponse.diameter)
        assertEquals(stubCIB.startYear, ctx.cibResponse.startYear)
        assertEquals(stubCIB.stopYear, ctx.cibResponse.stopYear)
    }

    @Test
    fun deleteCID() = runTest {

        val ctx = GrschbrContext(
            command = GrschbrCommand.DELETE,
            state = GrschbrState.NONE,
            type = GrschbrType.DETAIL,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.SUCCESS,
            cidRequest = cid,
        )
        processor.exec(ctx)

        assertEquals(cid.id, ctx.cidResponse.id)
        assertEquals(stubCID.description, ctx.cidResponse.description)
        assertEquals(stubCID.mint, ctx.cidResponse.mint)
        assertEquals(stubCID.copies, ctx.cidResponse.copies)
        assertEquals(stubCID.issueYear, ctx.cidResponse.issueYear)
        assertEquals(stubCID.cibId, ctx.cidResponse.cibId)
    }

    @Test
    fun badId() = runTest {
        val ctx = GrschbrContext(
            command = GrschbrCommand.DELETE,
            state = GrschbrState.NONE,
            type = GrschbrType.BASIC,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.BAD_ID,
            cibRequest = GrschbrCIB(),
        )
        processor.exec(ctx)
        assertEquals(GrschbrCIB(), ctx.cibResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun canNotDelete() = runTest {
        val ctx = GrschbrContext(
            command = GrschbrCommand.DELETE,
            state = GrschbrState.NONE,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.CANNOT_DELETE,
            cibRequest = GrschbrCIB(),
        )
        processor.exec(ctx)
        assertEquals(GrschbrCIB(), ctx.cibResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = GrschbrContext(
            command = GrschbrCommand.DELETE,
            state = GrschbrState.NONE,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.DB_ERROR,
            cibRequest = cib,
        )
        processor.exec(ctx)
        assertEquals(GrschbrCIB(), ctx.cibResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = GrschbrContext(
            command = GrschbrCommand.DELETE,
            state = GrschbrState.NONE,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.BAD_TITLE,
            cibRequest = cib,
        )
        processor.exec(ctx)
        assertEquals(GrschbrCIB(), ctx.cibResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
