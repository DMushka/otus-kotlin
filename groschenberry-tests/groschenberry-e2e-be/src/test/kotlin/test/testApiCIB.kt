package com.otus.otuskotlin.groschenberry.e2e.be.test

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldExist
import io.kotest.matchers.collections.shouldExistInOrder
import io.kotest.matchers.shouldBe
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBDebug
import com.otus.otuskotlin.groschenberry.api.v1.models.CISearchFilter
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBUpdateObject
import com.otus.otuskotlin.groschenberry.e2e.be.fixture.client.Client
import com.otus.otuskotlin.groschenberry.e2e.be.test.action.cib.debugStubCIB
import com.otus.otuskotlin.groschenberry.e2e.be.test.action.cib.*

fun FunSpec.testApiCIB(client: Client, prefix: String = "", debug: CIBDebug = debugStubCIB) {
    context("${prefix}v1") {
        test("Create CIB ok") {
            client.createCIB(debug = debug)
        }

        test("Read CIB ok") {
            val created = client.createCIB(debug = debug)
            client.readCIB(created.id, debug = debug).asClue {
                it shouldBe created
            }
        }

        test("Update CIB ok") {
            val created = client.createCIB(debug = debug)
            val updateAd = CIBUpdateObject(
                id = created.id,
                lock = created.lock,
                title = "new cib title",
                description = created.description,
            )
            client.updateCIB(updateAd, debug = debug)
        }

        test("Delete CIB ok") {
            val created = client.createCIB(debug = debug)
            client.deleteCIB(created, debug = debug)
//            client.readAd(created.id) {
//                 it should haveError("not-found")
//            }
        }

        test("Search CIB ok") {
            val created1 = client.createCIB(someCreateCIB.copy(description = "COIN Search 1"), debug = debug)
            val created2 = client.createCIB(someCreateCIB.copy(description = "COIN Search 2"), debug = debug)

            withClue("Search COIN") {
                val results = client.searchCIB(search = CISearchFilter(searchString = "COIN"), debug = debug)
                results shouldExist { it.description == created1.description }
                results shouldExist { it.description == created2.description }
            }

            withClue("Search 1") {
                client.searchCIB(search = CISearchFilter(searchString = "1"), debug = debug)
                    .shouldExistInOrder({ it.description == created1.description })
            }
        }
    }

}
