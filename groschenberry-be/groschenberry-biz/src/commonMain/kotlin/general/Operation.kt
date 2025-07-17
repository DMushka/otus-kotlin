package com.otus.otuskotlin.groschenberry.biz.general

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCommand
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.chain

fun ICorChainDsl<GrschbrContext>.operation(
    title: String,
    command: GrschbrCommand,
    block: ICorChainDsl<GrschbrContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == GrschbrState.RUNNING }
}
