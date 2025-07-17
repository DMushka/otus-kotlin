package com.otus.otuskotlin.groschenberry.biz.stubs

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.models.GrschbrWorkMode
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.chain

fun ICorChainDsl<GrschbrContext>.stubs(title: String, block: ICorChainDsl<GrschbrContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == GrschbrWorkMode.STUB && state == GrschbrState.RUNNING }
}
