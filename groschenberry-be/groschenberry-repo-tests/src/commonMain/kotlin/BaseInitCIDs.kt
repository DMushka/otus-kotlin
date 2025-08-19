package com.otus.otuskotlin.groschenberry.backend.repo.tests

import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub

abstract class BaseInitCIDs(private val op: String): IInitObjects<GrschbrCID> {
    open val lockOld: GrschbrCILock = GrschbrCILock("20000000-0000-0000-0000-000000000001")
    open val lockBad: GrschbrCILock = GrschbrCILock("20000000-0000-0000-0000-000000000009")
    
    fun createInitTestModel(
        suf: String,
        description: String = "",
        lock: GrschbrCILock = lockOld,
    ) = GrschbrCIDStub.get().apply {
        id = GrschbrCIId("cid-repo-$op-$suf")
        this.description = "$suf stub description.$description"
        permissionsClient.clear()
        this.lock = lock
    }
}
