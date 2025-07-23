package com.otus.otuskotlin.groschenberry.common.repo

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIB
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCID
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCILock

data class DbCIIdRequest(
    val id: GrschbrCIId,
    val lock: GrschbrCILock = GrschbrCILock.NONE,
) {
    constructor(cib: GrschbrCIB): this(cib.id, cib.lock)
    constructor(cid: GrschbrCID): this(cid.id, cid.lock)
}
