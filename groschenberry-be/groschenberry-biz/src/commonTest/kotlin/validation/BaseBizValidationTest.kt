package com.otus.otuskotlin.groschenberry.biz.validation

import com.otus.otuskotlin.groschenberry.biz.GrschbrCIProcessor
import com.otus.otuskotlin.groschenberry.common.GrschbrCorSettings
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCommand

abstract class BaseBizValidationTest {
    protected abstract val command: GrschbrCommand
    private val settings by lazy { GrschbrCorSettings() }
    protected val processor by lazy { GrschbrCIProcessor(settings) }
}
