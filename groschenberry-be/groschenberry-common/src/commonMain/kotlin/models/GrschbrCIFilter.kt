package com.otus.otuskotlin.groschenberry.common.models

import com.otus.otuskotlin.groschenberry.common.models.GrschbrSearchPermissions

data class GrschbrCIFilter(
    var searchString: String = "",
    var ownerId: GrschbrUserId = GrschbrUserId.NONE,
    var searchPermissions: MutableSet<GrschbrSearchPermissions> = mutableSetOf(),
) {
    fun deepCopy(): GrschbrCIFilter = copy()

    fun isEmpty() = this == NONE

    companion object {
        private val NONE = GrschbrCIFilter()
    }
}
