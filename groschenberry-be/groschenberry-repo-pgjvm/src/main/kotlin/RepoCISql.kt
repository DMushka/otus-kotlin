package com.otus.otuskotlin.groschenberry.backend.repo.postgresql

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import com.otus.otuskotlin.groschenberry.common.helpers.asGrschbrError
import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.common.repo.*
import com.otus.otuskotlin.groschenberry.repo.common.IRepoCIInitializable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class RepoCISql(
    properties: SqlProperties,
    private val randomUuid: () -> String = { uuid4().toString() }
) : IRepoCI, IRepoCIInitializable {
        
    private val driver = when {
        properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
        else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
    }

    private val conn = Database.connect(
        properties.url, driver, properties.user, properties.password
    )

    private suspend inline fun <T> transactionWrapper(crossinline block: () -> T, crossinline handle: (Exception) -> T): T =
        withContext(Dispatchers.IO) {
            try {
                transaction(conn) {
                    block()
                }
            } catch (e: Exception) {
                handle(e)
            }
        }

    private suspend inline fun transactionWrapper(crossinline block: () -> IDbCIResponse): IDbCIResponse =
        transactionWrapper(block) { DbCIResponseErr(it.asGrschbrError()) }


    private val cibTable = CIBTable("${properties.schema}.${properties.tableCIB}")

    fun clearCIB(): Unit = transaction(conn) {
        cibTable.deleteAll()
    }

    private fun saveCIBObj(cib: GrschbrCIB): GrschbrCIB = transaction(conn) {
        val res = cibTable
            .insert {
                to(it, cib, randomUuid)
            }
            .resultedValues
            ?.map { cibTable.from(it) }
        res?.first() ?: throw RuntimeException("BD error: insert statement returned empty result")
    }

    override fun saveCIB(cibs: Collection<GrschbrCIB>): Collection<GrschbrCIB> = cibs.map { saveCIBObj(it) }
    override suspend fun createCIB(rq: DbCIBRequest): IDbCIResponse = transactionWrapper {
        DbCIBResponseOk(saveCIBObj(rq.cib))
    }

    private fun readCIB(id: GrschbrCIId): IDbCIResponse {
        val res = cibTable.selectAll().where {
            cibTable.id eq id.asString()
        }.singleOrNull() ?: return errorNotFound(id)
        return DbCIBResponseOk(cibTable.from(res))
    }

    override suspend fun readCIB(rq: DbCIIdRequest): IDbCIResponse = transactionWrapper { readCIB(rq.id) }

    private suspend fun updateCIB(
        id: GrschbrCIId,
        lock: GrschbrCILock,
        block: (GrschbrCIB) -> IDbCIResponse
    ): IDbCIResponse =
        transactionWrapper {
            if (id == GrschbrCIId.NONE) return@transactionWrapper errorEmptyId

            val current = cibTable.selectAll().where { cibTable.id eq id.asString() }
                .singleOrNull()
                ?.let { cibTable.from(it) }

            when {
                current == null -> errorNotFound(id)
                current.lock != lock -> errorCIBRepoConcurrency(current, lock)
                else -> block(current)
            }
        }


    override suspend fun updateCIB(rq: DbCIBRequest): IDbCIResponse = updateCIB(rq.cib.id, rq.cib.lock) {
        cibTable.update({ cibTable.id eq rq.cib.id.asString() }) {
            to(it, rq.cib.copy(lock = GrschbrCILock(randomUuid())), randomUuid)
        }
        readCIB(rq.cib.id)
    }

    override suspend fun deleteCIB(rq: DbCIIdRequest): IDbCIResponse = updateCIB(rq.id, rq.lock) {
        cibTable.deleteWhere { id eq rq.id.asString() }
        DbCIBResponseOk(it)
    }

    override suspend fun searchCIB(rq: DbCIFilterRequest): IDbCIsResponse =
        transactionWrapper({
            val res = cibTable.selectAll().where {
                buildList {
                    add(Op.TRUE)

                    if (rq.descriptionFilter.isNotBlank()) {
                        add(
                            (cibTable.description like "%${rq.descriptionFilter}%")
                                    or (cibTable.description like "%${rq.descriptionFilter}%")
                        )
                    }

                }.reduce { a, b -> a and b }
            }
            DbCIBsResponseOk(data = res.map { cibTable.from(it) })
        }, {
            DbCIsResponseErr(it.asGrschbrError())
        })

    private val cidTable = CIDTable("${properties.schema}.${properties.tableCID}")

    fun clearCID(): Unit = transaction(conn) {
        cidTable.deleteAll()
    }

    private fun saveCIDObj(cid: GrschbrCID): GrschbrCID = transaction(conn) {
        val res = cidTable
            .insert {
                to(it, cid, randomUuid)
            }
            .resultedValues
            ?.map { cidTable.from(it) }
        res?.first() ?: throw RuntimeException("BD error: insert statement returned empty result")
    }

    override fun saveCID(cids: Collection<GrschbrCID>): Collection<GrschbrCID> = cids.map { saveCIDObj(it) }
    override suspend fun createCID(rq: DbCIDRequest): IDbCIResponse = transactionWrapper {
        DbCIDResponseOk(saveCIDObj(rq.cid))
    }

    private fun readCID(id: GrschbrCIId): IDbCIResponse {
        val res = cidTable.selectAll().where {
            cidTable.id eq id.asString()
        }.singleOrNull() ?: return errorNotFound(id)
        return DbCIDResponseOk(cidTable.from(res))
    }

    override suspend fun readCID(rq: DbCIIdRequest): IDbCIResponse = transactionWrapper { readCID(rq.id) }

    private suspend fun updateCID(
        id: GrschbrCIId,
        lock: GrschbrCILock,
        block: (GrschbrCID) -> IDbCIResponse
    ): IDbCIResponse =
        transactionWrapper {
            if (id == GrschbrCIId.NONE) return@transactionWrapper errorEmptyId

            val current = cidTable.selectAll().where { cidTable.id eq id.asString() }
                .singleOrNull()
                ?.let { cidTable.from(it) }

            when {
                current == null -> errorNotFound(id)
                current.lock != lock -> errorCIDRepoConcurrency(current, lock)
                else -> block(current)
            }
        }


    override suspend fun updateCID(rq: DbCIDRequest): IDbCIResponse = updateCID(rq.cid.id, rq.cid.lock) {
        cidTable.update({ cidTable.id eq rq.cid.id.asString() }) {
            to(it, rq.cid.copy(lock = GrschbrCILock(randomUuid())), randomUuid)
        }
        readCID(rq.cid.id)
    }

    override suspend fun deleteCID(rq: DbCIIdRequest): IDbCIResponse = updateCID(rq.id, rq.lock) {
        cidTable.deleteWhere { id eq rq.id.asString() }
        DbCIDResponseOk(it)
    }

    override suspend fun searchCID(rq: DbCIFilterRequest): IDbCIsResponse =
        transactionWrapper({
            val res = cidTable.selectAll().where {
                buildList {
                    add(Op.TRUE)

                    if (rq.descriptionFilter.isNotBlank()) {
                        add(
                            (cidTable.description like "%${rq.descriptionFilter}%")
                                    or (cidTable.description like "%${rq.descriptionFilter}%")
                        )
                    }

                }.reduce { a, b -> a and b }
            }
            DbCIDsResponseOk(data = res.map { cidTable.from(it) })
        }, {
            DbCIsResponseErr(it.asGrschbrError())
        })
}
