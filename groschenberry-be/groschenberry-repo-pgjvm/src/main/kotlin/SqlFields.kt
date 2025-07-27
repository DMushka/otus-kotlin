package com.otus.otuskotlin.groschenberry.backend.repo.postgresql

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId

object SqlFields {
    const val ID = "id"
    const val DESCRIPTION = "description"
    const val LOCK = "lock"
    const val LOCK_OLD = "lock_old"
    
    //CIB
    const val TITLE = "title"
    const val COUNTRY = "country"
    const val CURRENCY = "currency"
    const val NOMINAL = "nominal"
    const val MATERIAL = "material"
    const val DIAMETER = "diameter"
    const val START_YEAR = "start_year"
    const val STOP_YEAR = "stop_year"
    
    const val COUNTRY_TYPE = "country_type"
    const val COUNTRY_AUSTRALIA = "Australia"
    const val COUNTRY_BELARUS = "Belarus"
    const val COUNTRY_GREAT_BRITAIN = "Great Britain"
    const val COUNTRY_RUSSIA = "Russia"

    const val CURRENCY_TYPE = "currency_type"
    const val CURRENCY_RUB = "RUB"
    const val CURRENCY_EUR = "EUR"
    const val CURRENCY_USD = "USD"
    const val CURRENCY_CNY = "CNY"

    const val NOMINAL_TYPE = "nominal_type"
    const val NOMINAL_1 = 1
    const val NOMINAL_2 = 2
    const val NOMINAL_5 = 5
    const val NOMINAL_10 = 10
    const val NOMINAL_15 = 15
    const val NOMINAL_20 = 20
    const val NOMINAL_25 = 25
    const val NOMINAL_50 = 50
    const val NOMINAL_100 = 100
    
    const val FILTER_TITLE = DESCRIPTION
    const val DELETE_OK = "DELETE_OK"

    // CID
    const val ISSUE_YEAR = "issue_year"
    const val MINT = "mint"
    const val COPIES = "copies"
    const val CIB_ID = "cib_id"
    
    fun String.quoted() = "\"$this\""
    val allCIBFields = listOf(
        ID, DESCRIPTION, LOCK, TITLE, COUNTRY, CURRENCY, NOMINAL, MATERIAL, DIAMETER, START_YEAR, STOP_YEAR
    )
    val allCIDFields = listOf(
        ID, DESCRIPTION, LOCK, TITLE, MINT, COPIES, ISSUE_YEAR, CIB_ID,
    )
}
