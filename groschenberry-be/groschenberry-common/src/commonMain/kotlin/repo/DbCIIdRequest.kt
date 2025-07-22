package com.otus.otuskotlin.groschenberry.common.repo

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIB
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCID
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId

data class DbCIIdRequest(
    val id: GrschbrCIId,
) {
    constructor(cib: GrschbrCIB): this(cib.id)
    constructor(cid: GrschbrCID): this(cid.id)
}
