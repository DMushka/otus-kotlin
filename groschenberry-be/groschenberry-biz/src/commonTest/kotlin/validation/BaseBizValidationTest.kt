package com.otus.otuskotlin.groschenberry.biz.validation

import com.otus.otuskotlin.groschenberry.biz.GrschbrCIProcessor
import com.otus.otuskotlin.groschenberry.common.GrschbrCorSettings
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCommand
import com.otus.otuskotlin.groschenberry.repo.common.CIRepoInitialized
import com.otus.otuskotlin.groschenberry.repo.inmemory.CIRepoInMemory
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub

abstract class BaseBizValidationTest {
    protected abstract val command: GrschbrCommand
    private val repo = CIRepoInitialized(
        repo = CIRepoInMemory(),
        initCIBObjects = listOf(
            GrschbrCIBStub.get(),
        ),
        initCIDObjects = listOf(
            GrschbrCIDStub.get(),
            GrschbrCIDStub.get().apply {
                id = GrschbrCIId("123-234-abc-ABC")
            },
        )
    )
    private val settings by lazy { GrschbrCorSettings(repoTest = repo) }
    protected val processor by lazy { GrschbrCIProcessor(settings) }
}
