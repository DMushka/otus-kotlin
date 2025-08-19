package com.otus.otuskotlin.groschenberry.common.repo

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCI
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIB
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCID
import com.otus.otuskotlin.groschenberry.common.models.GrschbrError

sealed interface IDbCIResponse: IDbResponse<GrschbrCI>

data class DbCIBResponseOk(
    val data: GrschbrCIB
): IDbCIResponse

data class DbCIDResponseOk(
    val data: GrschbrCID
): IDbCIResponse

data class DbCIResponseErr(
    val errors: List<GrschbrError> = emptyList()
): IDbCIResponse {
    constructor(err: GrschbrError): this(listOf(err))
}

data class DbCIBResponseErrWithData(
    val data: GrschbrCIB,
    val errors: List<GrschbrError> = emptyList()
): IDbCIResponse {
    constructor(data: GrschbrCIB, err: GrschbrError): this(data, listOf(err))
}

data class DbCIDResponseErrWithData(
    val data: GrschbrCID,
    val errors: List<GrschbrError> = emptyList()
): IDbCIResponse {
    constructor(data: GrschbrCID, err: GrschbrError): this(data, listOf(err))
}
