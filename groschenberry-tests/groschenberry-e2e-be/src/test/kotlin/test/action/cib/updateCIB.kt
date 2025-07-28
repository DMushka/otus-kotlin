package com.otus.otuskotlin.groschenberry.e2e.be.test.action.cib

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import com.otus.otuskotlin.groschenberry.api.v1.models.*
import com.otus.otuskotlin.groschenberry.e2e.be.test.action.beValidId
import com.otus.otuskotlin.groschenberry.e2e.be.test.action.beValidLock
import com.otus.otuskotlin.groschenberry.e2e.be.fixture.client.Client

suspend fun Client.updateCIB(cib: CIBUpdateObject, debug: CIBDebug = debugStubCIB): CIBResponseObject =
    updateCIB(cib, debug = debug) {
        it should haveSuccessResult
        it.cib shouldNotBe null
        it.cib?.apply {
            if (cib.title != null)
                title shouldBe cib.title
            if (cib.description != null)
                description shouldBe cib.description
        }
        it.cib!!
    }

suspend fun <T> Client.updateCIB(cib: CIBUpdateObject, debug: CIBDebug = debugStubCIB, block: (CIBUpdateResponse) -> T): T {
    val id = cib.id
    val lock = cib.lock
    return withClue("updated: $id, lock: $lock, set: $cib") {
        id should beValidId
        lock should beValidLock

        val response = sendAndReceive(
            "update", CIBUpdateRequest(
                debug = debug,
                cib = cib.copy(id = id, lock = lock)
            )
        ) as CIBUpdateResponse

        response.asClue(block)
    }
}
