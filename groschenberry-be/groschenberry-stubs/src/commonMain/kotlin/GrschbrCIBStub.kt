package com.otus.otuskotlin.groschenberry.stubs

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIB
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIBId
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

/*    fun prepareOffersList(filter: String, type: GrschbrDealSide) = listOf(
        grschbrCIBSupply("s-666-01", filter, type),
        grschbrCIBSupply("s-666-02", filter, type),
        grschbrCIBSupply("s-666-03", filter, type),
        grschbrCIBSupply("s-666-04", filter, type),
        grschbrCIBSupply("s-666-05", filter, type),
        grschbrCIBSupply("s-666-06", filter, type),
    )*/

    private fun grschbrCIBDemand(id: String, filter: String, country: GrschbrCountry) =
        grschbrCIB(COIN1, id = id, filter = filter, country = country)

    /*private fun grschbrCIBSupply(id: String, filter: String, type: GrschbrDealSide) =
        grschbrCIB(AD_SUPPLY_BOLT1, id = id, filter = filter, type = type)
*/
    private fun grschbrCIB(base: GrschbrCIB, id: String, filter: String, country: GrschbrCountry) = base.copy(
        id = GrschbrCIBId(id),
        title = "$filter $id",
        description = "desc $filter $id",
        country = country,
    )

}
