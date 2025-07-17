package com.otus.otuskotlin.groschenberry.api.v1.mappers

import com.otus.otuskotlin.groschenberry.api.v1.models.CIBCreateObject
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBDeleteObject
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBReadObject
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBUpdateObject
import com.otus.otuskotlin.groschenberry.api.v1.models.CIDCreateObject
import com.otus.otuskotlin.groschenberry.api.v1.models.CIDDeleteObject
import com.otus.otuskotlin.groschenberry.api.v1.models.CIDReadObject
import com.otus.otuskotlin.groschenberry.api.v1.models.CIDUpdateObject
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIB
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCID
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCILock

fun GrschbrCIB.toTransportCreateCIB() = CIBCreateObject(
    title = title,
    description = description,
    country = country.toTransportCIB(),
    currency = currency.toTransportCIB(),
    nominal = nominal.toTransportCIB(),
    material = material.takeIf { it.isNotBlank() },
    diameter = diameter.takeIf { it != 0.0 },
    startYear = startYear.takeIf { it != "0000"},
    stopYear = stopYear.takeIf { it != "0000"}
    )

fun GrschbrCID.toTransportCreateCID() = CIDCreateObject(
    description = description,
    mint = mint.takeIf { it.isNotBlank() },
    copies = copies.takeIf { it != 0 },
    issueYear = issueYear.takeIf { it != "0000" },
    cibId = cibId.toTransportCI()
)

fun GrschbrCIB.toTransportReadCIB() = CIBReadObject(
    id = id.toTransportCI()
)

fun GrschbrCID.toTransportReadCID() = CIDReadObject(
    id = id.toTransportCI()
)

fun GrschbrCIB.toTransportUpdateCIB() = CIBUpdateObject(
    id = id.toTransportCI(),
    title = title,
    description = description,
    country = this.country.toTransportCIB(),
    currency = this.currency.toTransportCIB(),
    nominal = this.nominal.toTransportCIB(),
    material = this.material.takeIf { it.isNotBlank() },
    diameter = this.diameter.takeIf { it != 0.0 },
    startYear = this.startYear.takeIf { it != "0000"},
    stopYear = this.stopYear.takeIf { it != "0000"},
    lock = lock.toTransportCI(),
)

fun GrschbrCID.toTransportUpdateCID() = CIDUpdateObject(
    id = id.toTransportCI(),
    description = description,
    mint = mint.takeIf { it.isNotBlank() },
    copies = copies.takeIf { it != 0 },
    issueYear = issueYear.takeIf { it != "0000" },
    lock = lock.toTransportCI(),
    cibId = cibId.toTransportCI()
)

internal fun GrschbrCILock.toTransportCI() = takeIf { it != GrschbrCILock.NONE }?.asString()

fun GrschbrCIB.toTransportDeleteCIB() = CIBDeleteObject(
    id = id.toTransportCI(),
    lock = lock.toTransportCI(),
)

fun GrschbrCID.toTransportDeleteCID() = CIDDeleteObject(
    id = id.toTransportCI(),
    lock = lock.toTransportCI(),
)