package com.otus.otuskotlin.groschenberry.app.ktor.repo

import com.otus.otuskotlin.groschenberry.api.v1.models.CIRequestDebugMode
import com.otus.otuskotlin.groschenberry.app.ktor.GrschbrAppSettings
import com.otus.otuskotlin.groschenberry.common.GrschbrCorSettings
import com.otus.otuskotlin.groschenberry.common.repo.IRepoCI
import com.otus.otuskotlin.groschenberry.repo.common.CIRepoInitialized
import com.otus.otuskotlin.groschenberry.repo.inmemory.CIRepoInMemory

class V1CIRepoInmemoryTest : V1CIRepoBaseTest() {
    override val workMode = CIRequestDebugMode.TEST
    private fun grAppSettings(repo: IRepoCI) = GrschbrAppSettings(
        corSettings = GrschbrCorSettings(
            repoTest = repo
        )
    )
    
    override val appSettingsCreate: GrschbrAppSettings = grAppSettings(
        repo = CIRepoInitialized(CIRepoInMemory(randomUuid = { uuidNew }))
    )
    override val appSettingsRead: GrschbrAppSettings = grAppSettings(
        repo = CIRepoInitialized(
            CIRepoInMemory(randomUuid = { uuidNew }),
            initCIBObjects = listOf(initCIB),
        )
    )
    override val appSettingsUpdate: GrschbrAppSettings = grAppSettings(
        repo = CIRepoInitialized(
            CIRepoInMemory(randomUuid = { uuidNew }),
            initCIBObjects = listOf(initCIB),
        )
    )
    override val appSettingsDelete: GrschbrAppSettings = grAppSettings(
        repo = CIRepoInitialized(
            CIRepoInMemory(randomUuid = { uuidNew }),
            initCIBObjects = listOf(initCIB),
        )
    )
    override val appSettingsSearch: GrschbrAppSettings = grAppSettings(
        repo = CIRepoInitialized(
            CIRepoInMemory(randomUuid = { uuidNew }),
            initCIBObjects = listOf(initCIB),
        )
    )
}
