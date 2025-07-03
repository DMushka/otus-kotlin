package com.otus.otuskotlin.groschenberry.app.common

import com.otus.otuskotlin.groschenberry.biz.GrschbrCIProcessor
import com.otus.otuskotlin.groschenberry.common.GrschbrCorSettings

interface IGrschbrAppSettings {
    val processor: GrschbrCIProcessor
    val corSettings: GrschbrCorSettings
}
