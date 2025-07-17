package com.otus.otuskotlin.groschenberry.common.models

data class GrschbrCIB(
    var id: GrschbrCIId = GrschbrCIId.NONE,
    var title: String = "",
    var description: String = "",
    var lock: GrschbrCILock = GrschbrCILock.NONE,
    var country: GrschbrCountry = GrschbrCountry.NONE,
    var currency: GrschbrCurrency = GrschbrCurrency.NONE,
    var nominal: GrschbrNominal = GrschbrNominal.NONE,
    var material: String = "",
    var diameter: Double = 0.0,
    var startYear: String = "0000",
    var stopYear: String = "0000",
    val permissionsClient: MutableSet<GrschbrCIPermissionClient> = mutableSetOf()
) {
    fun deepCopy(): GrschbrCIB = copy(
        permissionsClient = permissionsClient.toMutableSet(),
    )

    fun isEmpty() = this == NONE

    companion object {
        private val NONE = GrschbrCIB()
    }
}
