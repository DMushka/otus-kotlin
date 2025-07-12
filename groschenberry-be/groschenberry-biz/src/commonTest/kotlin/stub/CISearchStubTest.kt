package com.otus.otuskotlin.groschenberry.biz.stub

import kotlinx.coroutines.test.runTest
import com.otus.otuskotlin.groschenberry.biz.GrschbrCIProcessor
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.common.stubs.GrschbrStubs
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class CISearchStubTest {

    private val processor = GrschbrCIProcessor()
    val filter = GrschbrCIFilter(searchString = "тестовый фильтр")

    @Test
    fun searchCIB() = runTest {

        val ctx = GrschbrContext(
            command = GrschbrCommand.SEARCH,
            state = GrschbrState.NONE,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.SUCCESS,
            type = GrschbrType.BASIC,
            ciFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.cibsResponse.size > 1)
        val first = ctx.cibsResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.title.contains(filter.searchString))
        assertTrue(first.description.contains(filter.searchString))
        with (GrschbrCIBStub.get()) {
            assertEquals(country, first.country)
            assertEquals(currency, first.currency)
            assertEquals(material, first.material)
            assertEquals(nominal, first.nominal)
            assertEquals(diameter, first.diameter)
            assertEquals(startYear, first.startYear)
            assertEquals(stopYear, first.stopYear)
        }
    }

    @Test
    fun searchCID() = runTest {

        val ctx = GrschbrContext(
            command = GrschbrCommand.SEARCH,
            state = GrschbrState.NONE,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.SUCCESS,
            type = GrschbrType.DETAIL,
            ciFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.cidsResponse.size > 1)
        val first = ctx.cidsResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.description.contains(filter.searchString))
        with (GrschbrCIDStub.get()) {
            assertEquals(mint, first.mint)
            assertEquals(copies, first.copies)
            assertEquals(issueYear, first.issueYear)
            assertEquals(cibId, first.cibId)
        }
    }

    @Test
    fun badSearchString() = runTest {
        val ctx = GrschbrContext(
            command = GrschbrCommand.SEARCH,
            state = GrschbrState.NONE,
            type = GrschbrType.BASIC,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.BAD_SEARCH_STRING,
            ciFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(GrschbrCIB(), ctx.cibResponse)
        assertEquals("search-string", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = GrschbrContext(
            command = GrschbrCommand.SEARCH,
            state = GrschbrState.NONE,
            type = GrschbrType.BASIC,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.DB_ERROR,
            ciFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(GrschbrCIB(), ctx.cibResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = GrschbrContext(
            command = GrschbrCommand.SEARCH,
            state = GrschbrState.NONE,
            type = GrschbrType.DETAIL,
            workMode = GrschbrWorkMode.STUB,
            stubCase = GrschbrStubs.CANNOT_DELETE,
            ciFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(GrschbrCID(), ctx.cidResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
