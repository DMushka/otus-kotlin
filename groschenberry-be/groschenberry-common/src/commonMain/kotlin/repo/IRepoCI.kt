package com.otus.otuskotlin.groschenberry.common.repo

interface IRepoCI {

    suspend fun createCIB(rq: DbCIBRequest): IDbCIResponse
    suspend fun readCIB(rq: DbCIIdRequest): IDbCIResponse
    suspend fun updateCIB(rq: DbCIBRequest): IDbCIResponse
    suspend fun deleteCIB(rq: DbCIIdRequest): IDbCIResponse
    suspend fun searchCIB(rq: DbCIFilterRequest): IDbCIsResponse

    suspend fun createCID(rq: DbCIDRequest): IDbCIResponse
    suspend fun readCID(rq: DbCIIdRequest): IDbCIResponse
    suspend fun updateCID(rq: DbCIDRequest): IDbCIResponse
    suspend fun deleteCID(rq: DbCIIdRequest): IDbCIResponse
    suspend fun searchCID(rq: DbCIFilterRequest): IDbCIsResponse

    companion object {
        val NONE = object : IRepoCI {
            override suspend fun createCIB(rq: DbCIBRequest): IDbCIResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun readCIB(rq: DbCIIdRequest): IDbCIResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun updateCIB(rq: DbCIBRequest): IDbCIResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun deleteCIB(rq: DbCIIdRequest): IDbCIResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun searchCIB(rq: DbCIFilterRequest): IDbCIsResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun createCID(rq: DbCIDRequest): IDbCIResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun readCID(rq: DbCIIdRequest): IDbCIResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun updateCID(rq: DbCIDRequest): IDbCIResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun deleteCID(rq: DbCIIdRequest): IDbCIResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun searchCID(rq: DbCIFilterRequest): IDbCIsResponse {
                throw NotImplementedError("Must not be used")
            }

        }
    }

}