package com.otus.otuskotlin.groschenberry.biz.validation

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker

fun ICorChainDsl<GrschbrContext>.validateMaterial(title: String, regex: Regex) = worker {
    this.title = title
    on { type == GrschbrType.BASIC && (cibValidated.material.isEmpty() || !cibValidated.material.contains(regex))}
    handle {
        validateStringField("material", cibValidated.material, regex)
    }
}
