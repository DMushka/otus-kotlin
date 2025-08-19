package com.otus.otuskotlin.groschenberry.e2e.be.test.action.cib

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import com.otus.otuskotlin.groschenberry.api.v1.models.*
import com.otus.otuskotlin.groschenberry.e2e.be.fixture.client.Client

suspend fun Client.searchCIB(search: CISearchFilter, debug: CIBDebug = debugStubCIB): List<CIBResponseObject> = searchCIB(search, debug = debug) {
    it should haveSuccessResult
    it.cibs ?: listOf()
}

suspend fun <T> Client.searchCIB(search: CISearchFilter, debug: CIBDebug = debugStubCIB, block: (CIBSearchResponse) -> T): T =
    withClue("searchCIB: $search") {
        val response = sendAndReceive(
            "search",
            CIBSearchRequest(
                debug = debug,
                ciFilter = search,
            )
        ) as CIBSearchResponse

        response.asClue(block)
    }
