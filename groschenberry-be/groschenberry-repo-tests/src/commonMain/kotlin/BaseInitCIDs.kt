package com.otus.otuskotlin.groschenberry.backend.repo.tests

import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub

abstract class BaseInitCIDs(private val op: String): IInitObjects<GrschbrCID> {
    fun createInitTestModel(
        suf: String,
        description: String = ""
    ) = GrschbrCIDStub.get().apply {
        id = GrschbrCIId("cid-repo-$op-$suf")
        this.description = "$suf stub description.$description"
        permissionsClient.clear()
    }
}
