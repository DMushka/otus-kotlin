package com.otus.otuskotlin.groschenberry.backend.repo.postgresql

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import com.otus.otuskotlin.groschenberry.common.models.*

class CIDTable(tableName: String) : Table(tableName) {
    val id = text(SqlFields.ID)
    val description = text(SqlFields.DESCRIPTION).nullable()
    val lock = text(SqlFields.LOCK)
    val mint = text(SqlFields.MINT).nullable()
    val copies = integer(SqlFields.COPIES).nullable()
    val issueYear = text(SqlFields.ISSUE_YEAR).nullable()
    val cidId = text(SqlFields.CIB_ID)
  
    override val primaryKey = PrimaryKey(id)

    fun from(res: ResultRow) = GrschbrCID(
        id = GrschbrCIId(res[id]),
        description = res[description] ?: "",
        lock = GrschbrCILock(res[lock]),
        mint = res[mint] ?: "",
        copies = res[copies] ?: 0,
        issueYear = res[issueYear] ?: "0000",
        cibId = GrschbrCIId(res[cidId])
    )

    fun to(it: UpdateBuilder<*>, cid: GrschbrCID, randomUuid: () -> String) {
        it[id] = cid.id.takeIf { it != GrschbrCIId.NONE }?.asString() ?: randomUuid()
        it[description] = cid.description.takeIf { it.isNotBlank() }
        it[mint] = cid.mint.takeIf { it.isNotBlank() }
        it[copies] = cid.copies.takeIf { it != 0 }
        it[issueYear] = cid.issueYear.takeIf { it != "0000" }
        it[cidId] = cid.cibId.takeIf { it != GrschbrCIId.NONE }?.asString() ?: "1"
        it[lock] = cid.lock.takeIf { it != GrschbrCILock.NONE }?.asString() ?: randomUuid()
    }

}

