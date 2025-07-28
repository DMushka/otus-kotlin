package com.otus.otuskotlin.groschenberry.e2e.be.test.action.cib

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldNotBe
import com.otus.otuskotlin.groschenberry.api.v1.models.*
import com.otus.otuskotlin.groschenberry.e2e.be.fixture.client.Client
import com.otus.otuskotlin.groschenberry.e2e.be.test.action.beValidId

suspend fun Client.readCIB(id: String?, debug: CIBDebug = debugStubCIB): CIBResponseObject = readCIB(id, debug = debug) {
    it should haveSuccessResult
    it.cib shouldNotBe null
    it.cib!!
}

suspend fun <T> Client.readCIB(id: String?, debug: CIBDebug = debugStubCIB, block: (CIBReadResponse) -> T): T =
    withClue("readCIB: $id") {
        id should beValidId

        val response = sendAndReceive(
            "read",
            CIBReadRequest(
                debug = debug,
                cib = CIBReadObject(id = id)
            )
        ) as CIBReadResponse

        response.asClue(block)
    }
