package com.otus.otuskotlin.groschenberry.repo.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.common.repo.*
import com.otus.otuskotlin.groschenberry.common.repo.CIRepoBase
import com.otus.otuskotlin.groschenberry.repo.common.IRepoCIInitializable
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class CIRepoInMemory(
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : CIRepoBase(), IRepoCI, IRepoCIInitializable {

    private val mutex: Mutex = Mutex()
    private val cacheCIB = Cache.Builder<String, CIBEntity>()
        .expireAfterWrite(ttl)
        .build()

    private val cacheCID = Cache.Builder<String, CIDEntity>()
        .expireAfterWrite(ttl)
        .build()

    override fun saveCIB(cibs: Collection<GrschbrCIB>) = cibs.map { cib: GrschbrCIB ->
        val entity = CIBEntity(cib)
        require(entity.id != null)
        cacheCIB.put(entity.id, entity)
        cib
    }

    override fun saveCID(cibs: Collection<GrschbrCID>) = cibs.map { cid: GrschbrCID ->
        val entity = CIDEntity(cid)
        require(entity.id != null)
        cacheCID.put(entity.id, entity)
        cid
    }

    override suspend fun createCIB(rq: DbCIBRequest): IDbCIResponse = tryCIMethod {
        val key = randomUuid()
        val cib = rq.cib.copy(id = GrschbrCIId(key))
        val entity = CIBEntity(cib)
        mutex.withLock {
            cacheCIB.put(key, entity)
        }
        DbCIBResponseOk(cib)
    }

    override suspend fun readCIB(rq: DbCIIdRequest): IDbCIResponse = tryCIMethod {
        val key = rq.id.takeIf { it != GrschbrCIId.NONE }?.asString() ?: return@tryCIMethod errorEmptyId
        mutex.withLock {
            cacheCIB.get(key)
                ?.let {
                    DbCIBResponseOk(it.toInternal())
                } ?: errorNotFound(rq.id)
        }
    }

    override suspend fun updateCIB(rq: DbCIBRequest): IDbCIResponse = tryCIMethod {
        val rqCIB = rq.cib
        val id = rqCIB.id.takeIf { it != GrschbrCIId.NONE } ?: return@tryCIMethod errorEmptyId
        val key = id.asString()

        mutex.withLock {
            val oldCIB = cacheCIB.get(key)?.toInternal()
            when {
                oldCIB == null -> errorNotFound(id)
                else -> {
                    val newCIB = rqCIB.copy()
                    val entity = CIBEntity(newCIB)
                    cacheCIB.put(key, entity)
                    DbCIBResponseOk(newCIB)
                }
            }
        }
    }


    override suspend fun deleteCIB(rq: DbCIIdRequest): IDbCIResponse = tryCIMethod {
        val id = rq.id.takeIf { it != GrschbrCIId.NONE } ?: return@tryCIMethod errorEmptyId
        val key = id.asString()

        mutex.withLock {
            val oldCIB = cacheCIB.get(key)?.toInternal()
            when {
                oldCIB == null -> errorNotFound(id)
                else -> {
                    cacheCIB.invalidate(key)
                    DbCIBResponseOk(oldCIB)
                }
            }
        }
    }

    /**
     * Поиск объявлений по фильтру
     * Если в фильтре не установлен какой-либо из параметров - по нему фильтрация не идет
     */
    override suspend fun searchCIB(rq: DbCIFilterRequest): IDbCIsResponse = tryCIsMethod {
        val result: List<GrschbrCIB> = cacheCIB.asMap().asSequence()
            .filter { entry ->
                rq.descriptionFilter.takeIf { it.isNotBlank() }?.let {
                    entry.value.description?.contains(it) ?: false
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        DbCIBsResponseOk(result)
    }

    override suspend fun createCID(rq: DbCIDRequest): IDbCIResponse = tryCIMethod {
        val key = randomUuid()
        val cid = rq.cid.copy(id = GrschbrCIId(key))
        val entity = CIDEntity(cid)
        mutex.withLock {
            cacheCID.put(key, entity)
        }
        DbCIDResponseOk(cid)
    }

    override suspend fun readCID(rq: DbCIIdRequest): IDbCIResponse = tryCIMethod {
        val key = rq.id.takeIf { it != GrschbrCIId.NONE }?.asString() ?: return@tryCIMethod errorEmptyId
        mutex.withLock {
            cacheCID.get(key)
                ?.let {
                    DbCIDResponseOk(it.toInternal())
                } ?: errorNotFound(rq.id)
        }
    }

    override suspend fun updateCID(rq: DbCIDRequest): IDbCIResponse = tryCIMethod {
        val rqCID = rq.cid
        val id = rqCID.id.takeIf { it != GrschbrCIId.NONE } ?: return@tryCIMethod errorEmptyId
        val key = id.asString()

        mutex.withLock {
            val oldCID = cacheCID.get(key)?.toInternal()
            when {
                oldCID == null -> errorNotFound(id)
                else -> {
                    val newCID = rqCID.copy()
                    val entity = CIDEntity(newCID)
                    cacheCID.put(key, entity)
                    DbCIDResponseOk(newCID)
                }
            }
        }
    }


    override suspend fun deleteCID(rq: DbCIIdRequest): IDbCIResponse = tryCIMethod {
        val id = rq.id.takeIf { it != GrschbrCIId.NONE } ?: return@tryCIMethod errorEmptyId
        val key = id.asString()

        mutex.withLock {
            val oldCID = cacheCID.get(key)?.toInternal()
            when {
                oldCID == null -> errorNotFound(id)
                else -> {
                    cacheCID.invalidate(key)
                    DbCIDResponseOk(oldCID)
                }
            }
        }
    }

    /**
     * Поиск объявлений по фильтру
     * Если в фильтре не установлен какой-либо из параметров - по нему фильтрация не идет
     */
    override suspend fun searchCID(rq: DbCIFilterRequest): IDbCIsResponse = tryCIsMethod {
        val result: List<GrschbrCID> = cacheCID.asMap().asSequence()
            .filter { entry ->
                rq.descriptionFilter.takeIf { it.isNotBlank() }?.let {
                    entry.value.description?.contains(it) ?: false
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        DbCIDsResponseOk(result)
    }
}
