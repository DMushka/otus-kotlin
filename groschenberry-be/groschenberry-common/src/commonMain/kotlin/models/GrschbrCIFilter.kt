package com.otus.otuskotlin.groschenberry.common.models

data class GrschbrCIFilter(
    var searchString: String = "",
    var ownerId: GrschbrUserId = GrschbrUserId.NONE,
) {
    fun deepCopy(): GrschbrCIFilter = copy()

    fun isEmpty() = this == NONE

    companion object {
        private val NONE = GrschbrCIFilter()
    }
}
