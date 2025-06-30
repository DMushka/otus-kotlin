package com.otus.otuskotlin.groschenberry.stubs

import com.otus.otuskotlin.groschenberry.common.models.*

object GrschbrCIBStubCoins {
    val COIN1: GrschbrCIB
        get() = GrschbrCIB(
            id = GrschbrCIId("111"),
            title = "Монета 1",
            description = "Монета стандартная круглая",
            country = GrschbrCountry.RUSSIA,
            currency = GrschbrCurrency.RUB,
            nominal = GrschbrNominal._1,
            material = "Медь",
            diameter = 5.0,
            startYear = "1956",
            stopYear = "1961",
            lock = GrschbrCILock("123"),
            permissionsClient = mutableSetOf(
                GrschbrCIPermissionClient.READ,
                GrschbrCIPermissionClient.UPDATE,
                GrschbrCIPermissionClient.DELETE,
            )
        )
    val COIN2 = COIN1.copy(nominal = GrschbrNominal._5)
}
