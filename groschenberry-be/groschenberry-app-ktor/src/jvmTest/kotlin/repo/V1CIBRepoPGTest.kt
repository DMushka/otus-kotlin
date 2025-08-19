package repo

import com.otus.otuskotlin.groschenberry.api.v1.models.CIRequestDebugMode
import com.otus.otuskotlin.groschenberry.app.ktor.GrschbrAppSettings
import com.otus.otuskotlin.groschenberry.app.ktor.repo.CIBRepoPGTest
import com.otus.otuskotlin.groschenberry.app.ktor.repo.V1CIBRepoBaseTest
import com.otus.otuskotlin.groschenberry.backend.repo.postgresql.RepoCISql
import com.otus.otuskotlin.groschenberry.common.GrschbrCorSettings
import com.otus.otuskotlin.groschenberry.common.repo.IRepoCI
import kotlin.test.BeforeTest
import kotlin.test.Ignore

@Ignore
open class V1CIBRepoPGTest : V1CIBRepoBaseTest() {
    override val workMode = CIRequestDebugMode.TEST

    private fun grbAppSettings(repo: IRepoCI) = GrschbrAppSettings(
        corSettings = GrschbrCorSettings(
            repoTest = repo,
            repoProd = repo,
        )
    )

    override val appSettingsCreate: GrschbrAppSettings by lazy {
        grbAppSettings(
            repo = CIBRepoPGTest.repoUnderTestContainer(
                randomUuid = { uuidNew }
            )
        )
    }
    override val appSettingsRead: GrschbrAppSettings by lazy {
        grbAppSettings(
            repo = CIBRepoPGTest.repoUnderTestContainer(
                initObjects = listOf(initCIB),
                randomUuid = { uuidNew }
            )
        )
    }
    override val appSettingsUpdate: GrschbrAppSettings by lazy {
        grbAppSettings(
            repo = CIBRepoPGTest.repoUnderTestContainer(
                initObjects = listOf(initCIB),
                randomUuid = { uuidNew }
            )
        )
    }
    override val appSettingsDelete: GrschbrAppSettings by lazy {
        grbAppSettings(
            repo = CIBRepoPGTest.repoUnderTestContainer(
                initObjects = listOf(initCIB),
                randomUuid = { uuidNew },
            )
        )
    }
    override val appSettingsSearch: GrschbrAppSettings by lazy {
        grbAppSettings(
            repo = CIBRepoPGTest.repoUnderTestContainer(
                initObjects = listOf(initCIB),
                randomUuid = { uuidNew },
            )
        )
    }

    private val cleanRepo = CIBRepoPGTest.repoUnderTestContainer()

    @BeforeTest
    fun beforeTest() {
        val pgRepo = cleanRepo.repo as RepoCISql
        pgRepo.clearCIB()
    }
}