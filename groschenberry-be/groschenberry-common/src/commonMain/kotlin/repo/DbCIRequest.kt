package com.otus.otuskotlin.groschenberry.common.repo

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIB
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCID

sealed interface IDbRequest<T>

data class DbCIBRequest(
    val cib: GrschbrCIB
) : IDbRequest<GrschbrCIB>

data class DbCIDRequest(
    val cid: GrschbrCID
) : IDbRequest<GrschbrCID>