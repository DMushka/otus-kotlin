package com.otus.otuskotlin.groschenberry.backend.repo.postgresql

import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject
import com.otus.otuskotlin.groschenberry.common.models.GrschbrNominal

fun Table.nominalEnumeration(
    columnName: String
) = customEnumeration(
    name = columnName,
    sql = SqlFields.NOMINAL_TYPE,
    fromDb = { value ->
        when (value as Int) {
            SqlFields.NOMINAL_1 -> GrschbrNominal._1
            SqlFields.NOMINAL_2 -> GrschbrNominal._2
            SqlFields.NOMINAL_5 -> GrschbrNominal._5
            SqlFields.NOMINAL_10 -> GrschbrNominal._10
            else -> GrschbrNominal.NONE
        }
    },
    toDb = { value ->
        when (value) {
            GrschbrNominal._1 -> PgNominal1
            GrschbrNominal._2 -> PgNominal2
            GrschbrNominal._5 -> PgNominal5
            GrschbrNominal._10 -> PgNominal10
            GrschbrNominal.NONE -> throw Exception("Wrong value of Nominal. NONE is unsupported")
            else -> PgNominal1
        }
    }
)

sealed class PgNominalValue(eValue: Int) : PGobject() {
    init {
        type = SqlFields.NOMINAL_TYPE
        value = eValue.toString()
    }
}

object PgNominal1: PgNominalValue(SqlFields.NOMINAL_1) {
    private fun readResolve(): Any = PgNominal1
}

object PgNominal2: PgNominalValue(SqlFields.NOMINAL_2) {
    private fun readResolve(): Any = PgNominal2
}

object PgNominal5: PgNominalValue(SqlFields.NOMINAL_5) {
    private fun readResolve(): Any = PgNominal5
}

object PgNominal10: PgNominalValue(SqlFields.NOMINAL_10) {
    private fun readResolve(): Any = PgNominal10
}
