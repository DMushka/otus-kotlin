package com.otus.otuskotlin.groschenberry.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class GrschbrRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = GrschbrRequestId("")
    }
}
