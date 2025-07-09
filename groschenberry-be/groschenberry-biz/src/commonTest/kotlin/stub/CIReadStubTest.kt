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

class CIReadStubTest {

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
    fun readCIB() = runTest {

        val ctx = GrschbrContext(
            command = GrschbrCommand.READ,
            state = GrschbrState.NONE,
            type = GrschbrType.BASIC,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.SUCCESS,
            cibRequest = cib,
        )
        processor.exec(ctx)
        with (stubCIB) {
            assertEquals(cib.id, ctx.cibResponse.id)
            assertEquals(title, ctx.cibResponse.title)
            assertEquals(description, ctx.cibResponse.description)
            assertEquals(country, ctx.cibResponse.country)
            assertEquals(currency, ctx.cibResponse.currency)
            assertEquals(material, ctx.cibResponse.material)
            assertEquals(nominal, ctx.cibResponse.nominal)
            assertEquals(diameter, ctx.cibResponse.diameter)
            assertEquals(startYear, ctx.cibResponse.startYear)
            assertEquals(stopYear, ctx.cibResponse.stopYear)
        }
    }

    @Test
    fun readCID() = runTest {

        val ctx = GrschbrContext(
            command = GrschbrCommand.READ,
            state = GrschbrState.NONE,
            type = GrschbrType.DETAIL,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.SUCCESS,
            cidRequest = cid,
        )
        processor.exec(ctx)
        with (stubCID) {
            assertEquals(cid.id, ctx.cidResponse.id)
            assertEquals(description, ctx.cidResponse.description)
            assertEquals(mint, ctx.cidResponse.mint)
            assertEquals(copies, ctx.cidResponse.copies)
            assertEquals(issueYear, ctx.cidResponse.issueYear)
            assertEquals(cibId, ctx.cidResponse.cibId)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = GrschbrContext(
            command = GrschbrCommand.READ,
            state = GrschbrState.NONE,
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
    fun databaseError() = runTest {
        val ctx = GrschbrContext(
            command = GrschbrCommand.READ,
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
            command = GrschbrCommand.READ,
            state = GrschbrState.NONE,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.CANNOT_DELETE,
            cidRequest = cid,
        )
        processor.exec(ctx)
        assertEquals(GrschbrCID(), ctx.cidResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
