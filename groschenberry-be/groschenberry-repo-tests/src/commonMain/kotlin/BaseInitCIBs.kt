package com.otus.otuskotlin.groschenberry.backend.repo.tests

import com.otus.otuskotlin.groschenberry.common.models.*
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub

abstract class BaseInitCIBs(private val op: String): IInitObjects<GrschbrCIB> {
    fun createInitTestModel(
        suf: String,
        searchValue: String = ""
    ) = GrschbrCIBStub.get().apply {
        id = GrschbrCIId("cib-repo-$op-$suf")
        title = "$suf stub"
        description = "$suf stub description.$searchValue"
        permissionsClient.clear()
    }
}
