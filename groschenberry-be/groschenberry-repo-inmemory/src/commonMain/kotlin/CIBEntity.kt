package com.otus.otuskotlin.groschenberry.repo.inmemory

import com.otus.otuskotlin.groschenberry.common.models.*

data class CIBEntity(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    var country: String? = null,
    var currency: String? = null,
    var nominal: Int? = null,
    var material: String? = null,
    var diameter: Double? = null,
    var startYear: String? = null,
    var stopYear: String? = null,
    val lock: String? = null,
) {
    constructor(model: GrschbrCIB): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        title = model.title.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        country = model.country.takeIf { it != GrschbrCountry.NONE }?.name,
        currency = model.currency.takeIf { it != GrschbrCurrency.NONE }?.name,
        nominal = model.nominal.takeIf { it != GrschbrNominal.NONE }?.value,
        material = model.material.takeIf { it.isNotBlank() },
        diameter = model.diameter.takeIf { it != 0.0 },
        startYear = model.startYear.takeIf { it != "0000"},
        stopYear = model.stopYear.takeIf { it != "0000"},
        lock = model.lock.asString().takeIf { it.isNotBlank() }
        // Не нужно сохранять permissions, потому что он ВЫЧИСЛЯЕМЫЙ, а не хранимый
    )

    fun toInternal() = GrschbrCIB(
        id = id?.let { GrschbrCIId(it) }?: GrschbrCIId.NONE,
        title = title?: "",
        description = description?: "",
        country = this.country?.let { GrschbrCountry.valueOf(it)} ?: GrschbrCountry.NONE,
        currency = this.currency?.let { GrschbrCurrency.valueOf(it)} ?: GrschbrCurrency.NONE,
        nominal = this.nominal?.let { GrschbrNominal.fromInt(it)} ?: GrschbrNominal.NONE,
        material = this.material ?: "",
        diameter = this.diameter ?: 0.0,
        startYear = this.startYear ?: "0000",
        stopYear = this.stopYear ?: "0000",
        lock = lock?.let { GrschbrCILock(it) } ?: GrschbrCILock.NONE,
    )
}
