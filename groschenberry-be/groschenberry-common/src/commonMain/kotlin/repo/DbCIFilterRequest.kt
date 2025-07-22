package com.otus.otuskotlin.groschenberry.common.repo

import com.otus.otuskotlin.groschenberry.common.models.GrschbrUserId

data class DbCIFilterRequest(
    val descriptionFilter: String = "",
    val ownerId: GrschbrUserId = GrschbrUserId.NONE,
    )
