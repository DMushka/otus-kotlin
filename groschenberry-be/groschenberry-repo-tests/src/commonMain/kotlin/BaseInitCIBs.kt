package com.otus.otuskotlin.groschenberry.backend.repo.tests

import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub

abstract class BaseInitCIBs(private val op: String): IInitObjects<GrschbrCIB> {
    open val lockOld: GrschbrCILock = GrschbrCILock("20000000-0000-0000-0000-000000000001")
    open val lockBad: GrschbrCILock = GrschbrCILock("20000000-0000-0000-0000-000000000009")
    
    fun createInitTestModel(
        suf: String,
        searchValue: String = "",
        lock: GrschbrCILock = lockOld,
    ) = GrschbrCIBStub.get().apply {
        id = GrschbrCIId("cib-repo-$op-$suf")
        title = "$suf stub"
        description = "$suf stub description.$searchValue"
        permissionsClient.clear()
        this.lock = lock
    }
}
