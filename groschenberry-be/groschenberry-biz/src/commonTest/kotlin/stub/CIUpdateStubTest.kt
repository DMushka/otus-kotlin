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

class CIUpdateStubTest {

    private val processor = GrschbrCIProcessor()
    val cib = GrschbrCIBStub.get().apply { id = GrschbrCIId("555") }
    val cid = GrschbrCIDStub.get().apply { id = GrschbrCIId("777") }

    @Test
    fun updateCIB() = runTest {

        val ctx = GrschbrContext(
            command = GrschbrCommand.UPDATE,
            state = GrschbrState.NONE,
            type = GrschbrType.BASIC,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.SUCCESS,
            cibRequest = cib,
        )
        processor.exec(ctx)
        assertEquals(cib.id, ctx.cibResponse.id)
        assertEquals(cib.title, ctx.cibResponse.title)
        assertEquals(cib.description, ctx.cibResponse.description)
        assertEquals(cib.country, ctx.cibResponse.country)
        assertEquals(cib.currency, ctx.cibResponse.currency)
        assertEquals(cib.material, ctx.cibResponse.material)
        assertEquals(cib.nominal, ctx.cibResponse.nominal)
        assertEquals(cib.diameter, ctx.cibResponse.diameter)
        assertEquals(cib.startYear, ctx.cibResponse.startYear)
        assertEquals(cib.stopYear, ctx.cibResponse.stopYear)
    }

    @Test
    fun updateCID() = runTest {

        val ctx = GrschbrContext(
            command = GrschbrCommand.UPDATE,
            state = GrschbrState.NONE,
            type = GrschbrType.DETAIL,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.SUCCESS,
            cidRequest = cid,
        )
        processor.exec(ctx)
        assertEquals(cid.id, ctx.cidResponse.id)
        assertEquals(cid.description, ctx.cidResponse.description)
        assertEquals(cid.mint, ctx.cidResponse.mint)
        assertEquals(cid.copies, ctx.cidResponse.copies)
        assertEquals(cid.issueYear, ctx.cidResponse.issueYear)
        assertEquals(cid.cibId, ctx.cidResponse.cibId)
    }

    @Test
    fun badTitle() = runTest {

        val ctx = GrschbrContext(
            command = GrschbrCommand.UPDATE,
            state = GrschbrState.NONE,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.BAD_TITLE,
            cibRequest = cib,
        )
        processor.exec(ctx)
        assertEquals(GrschbrCIB(), ctx.cibResponse)
        assertEquals("title", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badId() = runTest {

        val ctx = GrschbrContext(
            command = GrschbrCommand.UPDATE,
            state = GrschbrState.NONE,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.BAD_ID,
            cidRequest = cid,
        )
        processor.exec(ctx)
        assertEquals(GrschbrCID(), ctx.cidResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badDescription() = runTest {

        val ctx = GrschbrContext(
            command = GrschbrCommand.UPDATE,
            state = GrschbrState.NONE,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.BAD_DESCRIPTION,
            cibRequest = cib,
        )
        processor.exec(ctx)
        assertEquals(GrschbrCIB(), ctx.cibResponse)
        assertEquals("description", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badCountry() = runTest {

        val ctx = GrschbrContext(
            command = GrschbrCommand.UPDATE,
            state = GrschbrState.NONE,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.BAD_COUNTRY,
            cibRequest = cib,
        )
        processor.exec(ctx)
        assertEquals(GrschbrCIB(), ctx.cibResponse)
        assertEquals("country", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badCurrency() = runTest {

        val ctx = GrschbrContext(
            command = GrschbrCommand.UPDATE,
            state = GrschbrState.NONE,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.BAD_CURRENCY,
            cibRequest = cib,
        )
        processor.exec(ctx)
        assertEquals(GrschbrCIB(), ctx.cibResponse)
        assertEquals("currency", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badMaterial() = runTest {

        val ctx = GrschbrContext(
            command = GrschbrCommand.UPDATE,
            state = GrschbrState.NONE,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.BAD_MATERIAL,
            cibRequest = cib,
        )
        processor.exec(ctx)
        assertEquals(GrschbrCIB(), ctx.cibResponse)
        assertEquals("material", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNominal() = runTest {

        val ctx = GrschbrContext(
            command = GrschbrCommand.UPDATE,
            state = GrschbrState.NONE,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.BAD_NOMINAL,
            cibRequest = cib,
        )
        processor.exec(ctx)
        assertEquals(GrschbrCIB(), ctx.cibResponse)
        assertEquals("nominal", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badDiameter() = runTest {

        val ctx = GrschbrContext(
            command = GrschbrCommand.UPDATE,
            state = GrschbrState.NONE,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.BAD_DIAMETER,
            cibRequest = cib,
        )
        processor.exec(ctx)
        assertEquals(GrschbrCIB(), ctx.cibResponse)
        assertEquals("diameter", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badStartYear() = runTest {

        val ctx = GrschbrContext(
            command = GrschbrCommand.UPDATE,
            state = GrschbrState.NONE,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.BAD_START_YEAR,
            cibRequest = cib,
        )
        processor.exec(ctx)
        assertEquals(GrschbrCIB(), ctx.cibResponse)
        assertEquals("start-year", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badMint() = runTest {

        val ctx = GrschbrContext(
            command = GrschbrCommand.UPDATE,
            state = GrschbrState.NONE,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.BAD_MINT,
            cidRequest = cid,
        )
        processor.exec(ctx)
        assertEquals(GrschbrCIB(), ctx.cibResponse)
        assertEquals("mint", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badCopies() = runTest {

        val ctx = GrschbrContext(
            command = GrschbrCommand.UPDATE,
            state = GrschbrState.NONE,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.BAD_COPIES,
            cidRequest = cid,
        )
        processor.exec(ctx)
        assertEquals(GrschbrCIB(), ctx.cibResponse)
        assertEquals("copies", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badIssueYear() = runTest {

        val ctx = GrschbrContext(
            command = GrschbrCommand.UPDATE,
            state = GrschbrState.NONE,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.BAD_COPIES,
            cidRequest = cid,
        )
        processor.exec(ctx)
        assertEquals(GrschbrCIB(), ctx.cibResponse)
        assertEquals("copies", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badStopYear() = runTest {

        val ctx = GrschbrContext(
            command = GrschbrCommand.UPDATE,
            state = GrschbrState.NONE,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.BAD_STOP_YEAR,
            cibRequest = cib,
        )
        processor.exec(ctx)
        assertEquals(GrschbrCIB(), ctx.cibResponse)
        assertEquals("stop-year", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {

        val ctx = GrschbrContext(
            command = GrschbrCommand.UPDATE,
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
            command = GrschbrCommand.UPDATE,
            state = GrschbrState.NONE,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.BAD_SEARCH_STRING,
            cibRequest = cib,
        )
        processor.exec(ctx)
        assertEquals(GrschbrCIB(), ctx.cibResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
