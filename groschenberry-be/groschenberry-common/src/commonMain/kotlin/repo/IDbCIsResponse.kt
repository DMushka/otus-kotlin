package com.otus.otuskotlin.groschenberry.common.repo

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCI
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIB
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCID
import com.otus.otuskotlin.groschenberry.common.models.GrschbrError

sealed interface IDbCIsResponse: IDbResponse<List<GrschbrCI>>

data class DbCIBsResponseOk(
    val data: List<GrschbrCIB>
): IDbCIsResponse

data class DbCIDsResponseOk(
    val data: List<GrschbrCID>
): IDbCIsResponse

@Suppress("unused")
data class DbCIsResponseErr(
    val errors: List<GrschbrError> = emptyList()
): IDbCIsResponse {
    constructor(err: GrschbrError): this(listOf(err))
}