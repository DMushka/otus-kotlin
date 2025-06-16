package com.otus.otuskotlin.groschenberry.common.models

import kotlinx.serialization.SerialName

enum class GrschbrNominal (val value: Int) {
    NONE(0),
    _1(1),
    _2(2),
    _5(5),
    _10(10),
    _15(15),
    _20(20),
    _25(25),
    _50(50),
    _100(100);

    companion object {
        fun fromInt(value: Int) = GrschbrNominal.values().first { it.value == value }
    }
}