package com.otus.otuskotlin.groschenberry.common.models

data class GrschbrError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
)
