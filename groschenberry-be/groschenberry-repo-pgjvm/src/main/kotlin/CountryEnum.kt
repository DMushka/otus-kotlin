package com.otus.otuskotlin.groschenberry.backend.repo.postgresql

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCountry
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

fun Table.countryEnumeration(
    columnName: String
) = customEnumeration(
    name = columnName,
    sql = SqlFields.COUNTRY_TYPE,
    fromDb = { value ->
        when (value.toString()) {
            SqlFields.COUNTRY_AUSTRALIA -> GrschbrCountry.AUSTRALIA
            SqlFields.COUNTRY_BELARUS -> GrschbrCountry.BELARUS
            SqlFields.COUNTRY_GREAT_BRITAIN -> GrschbrCountry.GREAT_BRITAIN
            SqlFields.COUNTRY_RUSSIA -> GrschbrCountry.RUSSIA
            else -> GrschbrCountry.NONE
        }
    },
    toDb = { value ->
        when (value) {
            GrschbrCountry.AUSTRALIA -> PgCountryAustralia
            GrschbrCountry.BELARUS -> PgCountryBelarus
            GrschbrCountry.GREAT_BRITAIN -> PgCountryGreatBritain
            GrschbrCountry.RUSSIA -> PgCountryRussia
            GrschbrCountry.NONE -> throw Exception("Wrong value of Ad Type. NONE is unsupported")
        }
    }
)

sealed class PgCountryTypeValue(enVal: String): PGobject() {
    init {
        type = SqlFields.COUNTRY_TYPE
        value = enVal
    }
}

object PgCountryAustralia: PgCountryTypeValue(SqlFields.COUNTRY_AUSTRALIA) {
    private fun readResolve(): Any = PgCountryAustralia
}

object PgCountryBelarus: PgCountryTypeValue(SqlFields.COUNTRY_BELARUS) {
    private fun readResolve(): Any = PgCountryBelarus
}
object PgCountryGreatBritain: PgCountryTypeValue(SqlFields.COUNTRY_GREAT_BRITAIN) {
    private fun readResolve(): Any = PgCountryGreatBritain
}

object PgCountryRussia: PgCountryTypeValue(SqlFields.COUNTRY_RUSSIA) {
    private fun readResolve(): Any = PgCountryRussia
}

