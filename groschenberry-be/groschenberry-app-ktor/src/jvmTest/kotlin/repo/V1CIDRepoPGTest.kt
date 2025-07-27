package com.otus.otuskotlin.groschenberry.app.ktor.repo

import com.otus.otuskotlin.groschenberry.api.v1.models.CIRequestDebugMode
import com.otus.otuskotlin.groschenberry.app.ktor.GrschbrAppSettings
import com.otus.otuskotlin.groschenberry.backend.repo.postgresql.RepoCISql
import com.otus.otuskotlin.groschenberry.common.GrschbrCorSettings
import com.otus.otuskotlin.groschenberry.common.repo.IRepoCI
import kotlin.test.BeforeTest
import kotlin.test.Ignore

@Ignore
open class V1CIDRepoPGTest : V1CIDRepoBaseTest() {
    override val workMode = CIRequestDebugMode.TEST

    private fun grbAppSettings(repo: IRepoCI) = GrschbrAppSettings(
        corSettings = GrschbrCorSettings(
            repoTest = repo,
            repoProd = repo,
        )
    )

    override val appSettingsCreate: GrschbrAppSettings by lazy {
        grbAppSettings(
            repo = CIDRepoPGTest.repoUnderTestContainer(
                randomUuid = { uuidNew }
            )
        )
    }
    override val appSettingsRead: GrschbrAppSettings by lazy {
        grbAppSettings(
            repo = CIDRepoPGTest.repoUnderTestContainer(
                initObjects = listOf(initCID),
                randomUuid = { uuidNew }
            )
        )
    }
    override val appSettingsUpdate: GrschbrAppSettings by lazy {
        grbAppSettings(
            repo = CIDRepoPGTest.repoUnderTestContainer(
                initObjects = listOf(initCID),
                randomUuid = { uuidNew }
            )
        )
    }
    override val appSettingsDelete: GrschbrAppSettings by lazy {
        grbAppSettings(
            repo = CIDRepoPGTest.repoUnderTestContainer(
                initObjects = listOf(initCID),
                randomUuid = { uuidNew },
            )
        )
    }
    override val appSettingsSearch: GrschbrAppSettings by lazy {
        grbAppSettings(
            repo = CIDRepoPGTest.repoUnderTestContainer(
                initObjects = listOf(initCID),
                randomUuid = { uuidNew },
            )
        )
    }

    private val cleanRepo = CIDRepoPGTest.repoUnderTestContainer()

    @BeforeTest
    fun beforeTest() {
        val pgRepo = cleanRepo.repo as RepoCISql
        pgRepo.clearCID()
    }
}