package com.otus.otuskotlin.groschenberry.common.repo.exceptions

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId

open class RepoCIException(
    @Suppress("unused")
    val ciId: GrschbrCIId,
    msg: String,
): RepoException(msg)
