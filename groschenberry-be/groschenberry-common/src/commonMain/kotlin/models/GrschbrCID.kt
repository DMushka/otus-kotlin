package com.otus.otuskotlin.groschenberry.common.models

data class GrschbrCID(
    var id: GrschbrCIId = GrschbrCIId.Companion.NONE,
    var description: String = "",
    var lock: GrschbrCILock = GrschbrCILock.Companion.NONE,
    var issueYear: String = "0000",
    var mint: String = "",
    val copies: Int = 0,
    val permissionsClient: MutableSet<GrschbrCIPermissionClient> = mutableSetOf(),
    val cibId: GrschbrCIId = GrschbrCIId.Companion.NONE,
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = GrschbrCID()
    }
}
