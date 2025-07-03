package com.otus.otuskotlin.groschenberry.stubs

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCID
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStubCoins.COIND1

object GrschbrCIDStub {
    fun get(): GrschbrCID = COIND1.copy()

    fun prepareResult(block: GrschbrCID.() -> Unit): GrschbrCID = get().apply(block)

    fun prepareSearchList(filter: String, copies: Int) = listOf(
        grschbrCIDDemand("999-01", filter, copies),
        grschbrCIDDemand("999-02", filter, copies),
        grschbrCIDDemand("999-03", filter, copies),
        grschbrCIDDemand("999-04", filter, copies),
        grschbrCIDDemand("999-05", filter, copies),
        grschbrCIDDemand("999-06", filter, copies),
    )

    private fun grschbrCIDDemand(id: String, filter: String, copies: Int) =
        grschbrCID(COIND1, id = id, filter = filter, copies = copies)

    private fun grschbrCID(base: GrschbrCID, id: String, filter: String, copies: Int) = base.copy(
        id = GrschbrCIId(id),
        description = "desc $filter $id",
        copies = copies,
    )

}
