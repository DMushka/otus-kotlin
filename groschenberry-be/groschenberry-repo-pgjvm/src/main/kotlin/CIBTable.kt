package com.otus.otuskotlin.groschenberry.backend.repo.postgresql

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import com.otus.otuskotlin.groschenberry.common.models.*
import java.math.BigDecimal

class CIBTable(tableName: String) : Table(tableName) {
    val id = text(SqlFields.ID)
    val title = text(SqlFields.TITLE).nullable()
    val description = text(SqlFields.DESCRIPTION).nullable()
    val lock = text(SqlFields.LOCK)
    val country = countryEnumeration(SqlFields.COUNTRY).nullable()
    val currency = currencyEnumeration(SqlFields.CURRENCY).nullable()
    //val nominal = nominalEnumeration(SqlFields.NOMINAL).nullable()
    val nominal = integer(SqlFields.NOMINAL).nullable()
    val material = text(SqlFields.MATERIAL).nullable()
    val diameter = decimal(SqlFields.DIAMETER, 5, 2).nullable()
    val startYear = text(SqlFields.START_YEAR).nullable()
    val stopYear = text(SqlFields.STOP_YEAR).nullable()
    override val primaryKey = PrimaryKey(id)

    fun from(res: ResultRow) = GrschbrCIB(
        id = GrschbrCIId(res[id]),
        title = res[title] ?: "",
        description = res[description] ?: "",
        country = res[country] ?: GrschbrCountry.NONE,
        currency = res[currency] ?: GrschbrCurrency.NONE,
        nominal = res[nominal]?.let { GrschbrNominal.fromInt(it) }  ?: GrschbrNominal.NONE,
        material = res[material] ?: "",
        diameter = (res[diameter].toString().toDoubleOrNull()) ?: 0.0,
        startYear = res[startYear] ?: "0000",
        stopYear = res[stopYear] ?: "0000",
        lock = GrschbrCILock(res[lock]),
    )

    fun to(it: UpdateBuilder<*>, cib: GrschbrCIB, randomUuid: () -> String) {
        it[id] = cib.id.takeIf { it != GrschbrCIId.NONE }?.asString() ?: randomUuid()
        it[title] = cib.title.takeIf { it.isNotBlank() }
        it[description] = cib.description.takeIf { it.isNotBlank() }
        it[country] = cib.country.takeIf { it != GrschbrCountry.NONE }
        it[currency] = cib.currency.takeIf { it != GrschbrCurrency.NONE }
        it[nominal] = cib.nominal.takeIf { it != GrschbrNominal.NONE }?.value
        it[material] = cib.material.takeIf { it.isNotBlank() }
        it[diameter] = BigDecimal(cib.diameter)
        it[startYear] = cib.startYear.takeIf { it != "0000" }
        it[stopYear] = cib.stopYear.takeIf { it != "0000" }
        it[lock] = cib.lock.takeIf { it != GrschbrCILock.NONE }?.asString() ?: randomUuid()
    }

}

