package com.otus.otuskotlin.groschenberry.biz

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.GrschbrCorSettings
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCountry
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub

@Suppress("unused", "RedundantSuspendModifier")
class GrschbrCIProcessor(val corSettings: GrschbrCorSettings) {

    suspend fun exec(ctx: GrschbrContext) {
        ctx.cibResponse = GrschbrCIBStub.get()
        ctx.cibsResponse = GrschbrCIBStub.prepareSearchList("cib search", GrschbrCountry.BELARUS).toMutableList()
        ctx.state = GrschbrState.RUNNING
    }
}
