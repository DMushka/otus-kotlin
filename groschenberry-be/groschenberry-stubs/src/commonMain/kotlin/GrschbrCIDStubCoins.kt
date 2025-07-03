package com.otus.otuskotlin.groschenberry.stubs

import com.otus.otuskotlin.groschenberry.common.models.*

object GrschbrCIDStubCoins {
    val COIND1: GrschbrCID
        get() = GrschbrCID(
            id = GrschbrCIId("999"),
            description = "Монета стандартная круглая",
            mint = "Монетный двор 1",
            copies = 100,
            issueYear = "1961",
            lock = GrschbrCILock("123"),
            cibId = GrschbrCIId("111"),
            permissionsClient = mutableSetOf(
                GrschbrCIPermissionClient.READ,
                GrschbrCIPermissionClient.UPDATE,
                GrschbrCIPermissionClient.DELETE,
            )
        )
    val COIND2 = COIND1.copy(copies = 500)
}
