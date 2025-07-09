package com.otus.otuskotlin.groschenberry.common.models

data class GrschbrCID(
    var id: GrschbrCIId = GrschbrCIId.Companion.NONE,
    var description: String = "",
    var lock: GrschbrCILock = GrschbrCILock.Companion.NONE,
    var issueYear: String = "0000",
    var mint: String = "",
    var copies: Int = 0,
    var permissionsClient: MutableSet<GrschbrCIPermissionClient> = mutableSetOf(),
    var cibId: GrschbrCIId = GrschbrCIId.Companion.NONE,
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = GrschbrCID()
    }
}
