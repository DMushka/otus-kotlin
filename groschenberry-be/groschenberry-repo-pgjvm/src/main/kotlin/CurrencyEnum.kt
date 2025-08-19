package com.otus.otuskotlin.groschenberry.backend.repo.postgresql

import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCurrency

fun Table.currencyEnumeration(
    columnName: String
) = customEnumeration(
    name = columnName,
    sql = SqlFields.CURRENCY_TYPE,
    fromDb = { value ->
        when (value.toString()) {
            SqlFields.CURRENCY_RUB -> GrschbrCurrency.RUB
            SqlFields.CURRENCY_EUR -> GrschbrCurrency.EUR
            SqlFields.CURRENCY_USD -> GrschbrCurrency.USD
            SqlFields.CURRENCY_CNY -> GrschbrCurrency.CNY
            else -> GrschbrCurrency.NONE
        }
    },
    toDb = { value ->
        when (value) {
            GrschbrCurrency.RUB -> PgCurrencyRUB
            GrschbrCurrency.EUR -> PgCurrencyEUR
            GrschbrCurrency.USD -> PgCurrencyUSD
            GrschbrCurrency.CNY -> PgCurrencyCNY
            GrschbrCurrency.NONE -> throw Exception("Wrong value of Currency. NONE is unsupported")
        }
    }
)

sealed class PgCurrencyValue(eValue: String) : PGobject() {
    init {
        type = SqlFields.CURRENCY_TYPE
        value = eValue
    }
}

object PgCurrencyRUB: PgCurrencyValue(SqlFields.CURRENCY_RUB) {
    private fun readResolve(): Any = PgCurrencyRUB
}

object PgCurrencyEUR: PgCurrencyValue(SqlFields.CURRENCY_EUR) {
    private fun readResolve(): Any = PgCurrencyEUR
}

object PgCurrencyUSD: PgCurrencyValue(SqlFields.CURRENCY_USD) {
    private fun readResolve(): Any = PgCurrencyUSD
}

object PgCurrencyCNY: PgCurrencyValue(SqlFields.CURRENCY_CNY) {
    private fun readResolve(): Any = PgCurrencyCNY
}
