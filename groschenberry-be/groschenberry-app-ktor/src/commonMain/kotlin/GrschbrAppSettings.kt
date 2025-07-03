package com.otus.otuskotlin.groschenberry.app.ktor

import com.otus.otuskotlin.groschenberry.app.common.IGrschbrAppSettings
import com.otus.otuskotlin.groschenberry.biz.GrschbrCIProcessor
import com.otus.otuskotlin.groschenberry.common.GrschbrCorSettings

data class GrschbrAppSettings(
    val appUrls: List<String> = emptyList(),
    override val corSettings: GrschbrCorSettings = GrschbrCorSettings(),
    override val processor: GrschbrCIProcessor = GrschbrCIProcessor(corSettings),
): IGrschbrAppSettings
