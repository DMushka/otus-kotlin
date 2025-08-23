package com.otus.otuskotlin.groschenberry.common.models

import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrPrincipalRelations

data class GrschbrCIB(
    var id: GrschbrCIId = GrschbrCIId.Companion.NONE,
    var description: String = "",
    var title: String = "",
    var lock: GrschbrCILock = GrschbrCILock.NONE,
    var country: GrschbrCountry = GrschbrCountry.NONE,
    var currency: GrschbrCurrency = GrschbrCurrency.NONE,
    var nominal: GrschbrNominal = GrschbrNominal.NONE,
    var material: String = "",
    var diameter: Double = 0.0,
    var startYear: String = "0000",
    var stopYear: String = "0000",
    // Результат вычисления отношений текущего пользователя (который сделал запрос) к текущему объявлению
    var principalRelations: Set<GrschbrPrincipalRelations> = emptySet(),
    // Набор пермишинов, которые отдадим во фронтенд
    val permissionsClient: MutableSet<GrschbrCIPermissionClient> = mutableSetOf(),
) : GrschbrCI() {

    fun deepCopy(): GrschbrCIB = copy(
        permissionsClient = permissionsClient.toMutableSet(),
    )

    fun isEmpty() = this == NONE

    companion object {
        private val NONE = GrschbrCIB()
    }
}
