package com.otus.otuskotlin.groschenberry.app.ktor.repo

import com.otus.otuskotlin.groschenberry.api.v1.models.*
import com.otus.otuskotlin.groschenberry.app.ktor.GrschbrAppSettings

abstract class V1CIRepoBaseTest {
    abstract val workMode: CIRequestDebugMode
    abstract val appSettingsCreate: GrschbrAppSettings
    abstract val appSettingsRead:   GrschbrAppSettings
    abstract val appSettingsUpdate: GrschbrAppSettings
    abstract val appSettingsDelete: GrschbrAppSettings
    abstract val appSettingsSearch: GrschbrAppSettings

    protected val uuidOld = "10000000-0000-0000-0000-000000000001"
    protected val uuidNew = "10000000-0000-0000-0000-000000000002"
}
