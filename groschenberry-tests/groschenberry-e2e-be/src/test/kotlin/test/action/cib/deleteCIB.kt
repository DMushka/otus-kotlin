package com.otus.otuskotlin.groschenberry.e2e.be.test.action.cib

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import com.otus.otuskotlin.groschenberry.api.v1.models.*
import com.otus.otuskotlin.groschenberry.e2e.be.test.action.beValidId
import com.otus.otuskotlin.groschenberry.e2e.be.test.action.beValidLock
import com.otus.otuskotlin.groschenberry.e2e.be.fixture.client.Client

suspend fun Client.deleteCIB(cib: CIBResponseObject, debug: CIBDebug = debugStubCIB) {
    val id = cib.id
    val lock = cib.lock
    withClue("deleteCIB: $id, lock: $lock") {
        id should beValidId
        lock should beValidLock

        val response = sendAndReceive(
            "delete",
            CIBDeleteRequest(
                debug = debug,
                cib = CIBDeleteObject(id = id, lock = lock)
            )
        ) as CIBDeleteResponse

        response.asClue {
            response should haveSuccessResult
            response.cib shouldBe cib
//            response.cib?.id shouldBe id
        }
    }
}
