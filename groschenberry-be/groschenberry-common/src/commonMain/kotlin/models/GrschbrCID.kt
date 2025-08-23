package com.otus.otuskotlin.groschenberry.common.models

import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrPrincipalRelations

data class GrschbrCID(
    var id: GrschbrCIId = GrschbrCIId.Companion.NONE,
    var description: String = "",
    var lock: GrschbrCILock = GrschbrCILock.Companion.NONE,
    var issueYear: String = "0000",
    var mint: String = "",
    var copies: Int = 0,
    // Результат вычисления отношений текущего пользователя (который сделал запрос) к текущему объявлению
    var principalRelations: Set<GrschbrPrincipalRelations> = emptySet(),
    // Набор пермишинов, которые отдадим во фронтенд
    val permissionsClient: MutableSet<GrschbrCIPermissionClient> = mutableSetOf(),
    var cibId: GrschbrCIId = GrschbrCIId.Companion.NONE,
) : GrschbrCI() {
    fun deepCopy(): GrschbrCID = copy(
        permissionsClient = permissionsClient.toMutableSet(),
    )

    fun isEmpty() = this == NONE

    companion object {
        private val NONE = GrschbrCID()
    }
}
