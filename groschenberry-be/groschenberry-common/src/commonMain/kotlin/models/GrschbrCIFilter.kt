package com.otus.otuskotlin.groschenberry.common.models

data class GrschbrCIFilter(
    var searchString: String = "",
    var ownerId: GrschbrUserId = GrschbrUserId.NONE,
)
