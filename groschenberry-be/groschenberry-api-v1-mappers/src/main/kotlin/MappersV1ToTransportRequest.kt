package com.otus.otuskotlin.groschenberry.mappers.v1

import com.otus.otuskotlin.groschenberry.api.v1.models.CIBCreateObject
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBDeleteObject
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBReadObject
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBUpdateObject
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIB
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIBId
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCILock

fun GrschbrCIB.toTransportCreateCIB() = CIBCreateObject(
    title = title,
    description = description,
    country = country.toTransport(),
    currency = currency.toTransport(),
    nominal = nominal.toTransportCIB(),
    material = material.takeIf { it.isNotBlank() },
    diameter = diameter.takeIf { it != 0.0 },
    startYear = startYear.takeIf { it != "0000"},
    stopYear = stopYear.takeIf { it != "0000"}
    )

fun GrschbrCIB.toTransportReadCIB() = CIBReadObject(
    id = id.toTransportCIB()
)

fun GrschbrCIB.toTransportUpdateCIB() = CIBUpdateObject(
    id = id.toTransportCIB(),
    title = title,
    description = description,
    country = this.country.toTransport(),
    currency = this.currency.toTransport(),
    nominal = this.nominal.toTransportCIB(),
    material = this.material.takeIf { it.isNotBlank() },
    diameter = this.diameter.takeIf { it != 0.0 },
    startYear = this.startYear.takeIf { it != "0000"},
    stopYear = this.stopYear.takeIf { it != "0000"},
    lock = lock.toTransportCIB(),
)

internal fun GrschbrCILock.toTransportCIB() = takeIf { it != GrschbrCILock.NONE }?.asString()

fun GrschbrCIB.toTransportDeleteCIB() = CIBDeleteObject(
    id = id.toTransportCIB(),
    lock = lock.toTransportCIB(),
)