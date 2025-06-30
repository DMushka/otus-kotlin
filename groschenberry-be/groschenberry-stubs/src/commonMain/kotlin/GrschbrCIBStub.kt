package com.otus.otuskotlin.groschenberry.stubs

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIB
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCountry
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStubCoins.COIN1

object GrschbrCIBStub {
    fun get(): GrschbrCIB = COIN1.copy()

    fun prepareResult(block: GrschbrCIB.() -> Unit): GrschbrCIB = get().apply(block)

    fun prepareSearchList(filter: String, country: GrschbrCountry) = listOf(
        grschbrCIBDemand("111-01", filter, country),
        grschbrCIBDemand("111-02", filter, country),
        grschbrCIBDemand("111-03", filter, country),
        grschbrCIBDemand("111-04", filter, country),
        grschbrCIBDemand("111-05", filter, country),
        grschbrCIBDemand("111-06", filter, country),
    )

    private fun grschbrCIBDemand(id: String, filter: String, country: GrschbrCountry) =
        grschbrCIB(COIN1, id = id, filter = filter, country = country)

    private fun grschbrCIB(base: GrschbrCIB, id: String, filter: String, country: GrschbrCountry) = base.copy(
        id = GrschbrCIId(id),
        title = "$filter $id",
        description = "desc $filter $id",
        country = country,
    )

}
