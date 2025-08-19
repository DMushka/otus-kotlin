package com.otus.otuskotlin.groschenberry.e2e.be.test.action.cib

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldMatch
import com.otus.otuskotlin.groschenberry.api.v1.models.*
import com.otus.otuskotlin.groschenberry.e2e.be.fixture.client.Client

suspend fun Client.createCIB(cib: CIBCreateObject = someCreateCIB, debug: CIBDebug = debugStubCIB): CIBResponseObject = createCIB(cib, debug = debug) {
    it should haveSuccessResult
    it.cib shouldNotBe null
    it.cib?.apply {
        title shouldBe cib.title
        description shouldBe cib.description
        id.toString() shouldMatch Regex("^[\\d\\w_-]+\$")
        lock.toString() shouldMatch Regex("^[\\d\\w_-]+\$")
    }
    it.cib!!
}

suspend fun <T> Client.createCIB(cib: CIBCreateObject = someCreateCIB, debug: CIBDebug = debugStubCIB, block: (CIBCreateResponse) -> T): T =
    withClue("createCIB: $cib") {
        val response = sendAndReceive(
            "create", CIBCreateRequest(
                debug = debug,
                cib = cib
            )
        ) as CIBCreateResponse

        response.asClue(block)
    }
