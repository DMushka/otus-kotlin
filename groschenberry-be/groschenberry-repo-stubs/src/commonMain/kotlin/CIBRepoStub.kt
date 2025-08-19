package com.otus.otuskotlin.groschenberry.backend.repository.inmemory

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCountry
import com.otus.otuskotlin.groschenberry.common.repo.*
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub

class CIRepoStub() : IRepoCI {
    override suspend fun createCIB(rq: DbCIBRequest): IDbCIResponse {
        return DbCIBResponseOk(
            data = GrschbrCIBStub.get(),
        )
    }

    override suspend fun readCIB(rq: DbCIIdRequest): IDbCIResponse {
        return DbCIBResponseOk(
            data = GrschbrCIBStub.get(),
        )
    }

    override suspend fun updateCIB(rq: DbCIBRequest): IDbCIResponse {
        return DbCIBResponseOk(
            data = GrschbrCIBStub.get(),
        )
    }

    override suspend fun deleteCIB(rq: DbCIIdRequest): IDbCIResponse {
        return DbCIBResponseOk(
            data = GrschbrCIBStub.get(),
        )
    }

    override suspend fun searchCIB(rq: DbCIFilterRequest): IDbCIsResponse {
        return DbCIBsResponseOk(
            data = GrschbrCIBStub.prepareSearchList(filter = "", GrschbrCountry.BELARUS),
        )
    }

    override suspend fun createCID(rq: DbCIDRequest): IDbCIResponse {
        return DbCIDResponseOk(
            data = GrschbrCIDStub.get(),
        )
    }

    override suspend fun readCID(rq: DbCIIdRequest): IDbCIResponse {
        return DbCIDResponseOk(
            data = GrschbrCIDStub.get(),
        )
    }

    override suspend fun updateCID(rq: DbCIDRequest): IDbCIResponse {
        return DbCIDResponseOk(
            data = GrschbrCIDStub.get(),
        )
    }

    override suspend fun deleteCID(rq: DbCIIdRequest): IDbCIResponse {
        return DbCIDResponseOk(
            data = GrschbrCIDStub.get(),
        )
    }

    override suspend fun searchCID(rq: DbCIFilterRequest): IDbCIsResponse {
        return DbCIDsResponseOk(
            data = GrschbrCIDStub.prepareSearchList(filter = "", 123),
        )
    }
}
