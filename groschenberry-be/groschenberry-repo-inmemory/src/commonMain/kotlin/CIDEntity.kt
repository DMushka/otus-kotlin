package com.otus.otuskotlin.groschenberry.repo.inmemory

import com.otus.otuskotlin.groschenberry.common.models.*

data class CIDEntity(
    val id: String? = null,
    val description: String? = null,
    var issueYear: String? = null,
    var mint: String? = null,
    var copies: Int? = null,
    var cibId: String? = null,
    var permissionsClient: MutableSet<GrschbrCIPermissionClient> = mutableSetOf(),
    val lock: String? = null,
) {
    constructor(model: GrschbrCID): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        mint = model.mint.takeIf { it.isNotBlank() },
        copies = model.copies.takeIf { it != 0 },
        issueYear = model.issueYear.takeIf { it != "0000"},
        lock = model.lock.asString().takeIf { it.isNotBlank() },
        cibId = model.cibId.asString().takeIf { it.isNotBlank() },
        // Не нужно сохранять permissions, потому что он ВЫЧИСЛЯЕМЫЙ, а не хранимый
    )

    fun toInternal() = GrschbrCID(
        id = id?.let { GrschbrCIId(it) }?: GrschbrCIId.NONE,
        description = description?: "",
        mint = this.mint ?: "",
        copies = this.copies ?: 0,
        issueYear = this.issueYear ?: "0000",
        cibId = cibId?.let { GrschbrCIId(it) }?: GrschbrCIId.NONE,
        lock = lock?.let { GrschbrCILock(it) } ?: GrschbrCILock.NONE,
    )
}
