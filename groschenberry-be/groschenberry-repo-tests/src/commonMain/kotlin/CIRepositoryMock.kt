package com.otus.otuskotlin.groschenberry.backend.repo.tests

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIB
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCID
import com.otus.otuskotlin.groschenberry.common.repo.*

class CIRepositoryMock(
    private val invokeCreateCIB: (DbCIBRequest) -> IDbCIResponse = { DEFAULT_CIB_SUCCESS_EMPTY_MOCK },
    private val invokeReadCIB: (DbCIIdRequest) -> IDbCIResponse = { DEFAULT_CIB_SUCCESS_EMPTY_MOCK },
    private val invokeUpdateCIB: (DbCIBRequest) -> IDbCIResponse = { DEFAULT_CIB_SUCCESS_EMPTY_MOCK },
    private val invokeDeleteCIB: (DbCIIdRequest) -> IDbCIResponse = { DEFAULT_CIB_SUCCESS_EMPTY_MOCK },
    private val invokeSearchCIB: (DbCIFilterRequest) -> IDbCIsResponse = { DEFAULT_CIBS_SUCCESS_EMPTY_MOCK },
    private val invokeCreateCID: (DbCIDRequest) -> IDbCIResponse = { DEFAULT_CID_SUCCESS_EMPTY_MOCK },
    private val invokeReadCID: (DbCIIdRequest) -> IDbCIResponse = { DEFAULT_CID_SUCCESS_EMPTY_MOCK },
    private val invokeUpdateCID: (DbCIDRequest) -> IDbCIResponse = { DEFAULT_CID_SUCCESS_EMPTY_MOCK },
    private val invokeDeleteCID: (DbCIIdRequest) -> IDbCIResponse = { DEFAULT_CID_SUCCESS_EMPTY_MOCK },
    private val invokeSearchCID: (DbCIFilterRequest) -> IDbCIsResponse = { DEFAULT_CIDS_SUCCESS_EMPTY_MOCK },

    ): IRepoCI {
    override suspend fun createCIB(rq: DbCIBRequest): IDbCIResponse {
        return invokeCreateCIB(rq)
    }

    override suspend fun readCIB(rq: DbCIIdRequest): IDbCIResponse {
        return invokeReadCIB(rq)
    }

    override suspend fun updateCIB(rq: DbCIBRequest): IDbCIResponse {
        return invokeUpdateCIB(rq)
    }

    override suspend fun deleteCIB(rq: DbCIIdRequest): IDbCIResponse {
        return invokeDeleteCIB(rq)
    }

    override suspend fun searchCIB(rq: DbCIFilterRequest): IDbCIsResponse {
        return invokeSearchCIB(rq)
    }

    override suspend fun createCID(rq: DbCIDRequest): IDbCIResponse {
        return invokeCreateCID(rq)
    }

    override suspend fun readCID(rq: DbCIIdRequest): IDbCIResponse {
        return invokeReadCID(rq)
    }

    override suspend fun updateCID(rq: DbCIDRequest): IDbCIResponse {
        return invokeUpdateCID(rq)
    }

    override suspend fun deleteCID(rq: DbCIIdRequest): IDbCIResponse {
        return invokeDeleteCID(rq)
    }

    override suspend fun searchCID(rq: DbCIFilterRequest): IDbCIsResponse {
        return invokeSearchCID(rq)
    }

    companion object {
        val DEFAULT_CIB_SUCCESS_EMPTY_MOCK = DbCIBResponseOk(GrschbrCIB())
        val DEFAULT_CIBS_SUCCESS_EMPTY_MOCK = DbCIBsResponseOk(emptyList())
        val DEFAULT_CID_SUCCESS_EMPTY_MOCK = DbCIDResponseOk(GrschbrCID())
        val DEFAULT_CIDS_SUCCESS_EMPTY_MOCK = DbCIDsResponseOk(emptyList())
    }
}
