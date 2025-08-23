package com.otus.otuskotlin.groschenberry.e2e.be.test

import io.kotest.core.annotation.Ignored
import com.otus.otuskotlin.groschenberry.blackbox.fixture.docker.DockerCompose
import com.otus.otuskotlin.groschenberry.e2e.be.docker.KtorJvmKeycloakDockerCompose
import com.otus.otuskotlin.groschenberry.e2e.be.docker.KtorJvmPGDockerCompose
import com.otus.otuskotlin.groschenberry.e2e.be.docker.WiremockDockerCompose
import com.otus.otuskotlin.groschenberry.e2e.be.fixture.BaseFunSpec
import com.otus.otuskotlin.groschenberry.e2e.be.fixture.client.RestAuthClient
import com.otus.otuskotlin.groschenberry.e2e.be.fixture.client.RestClient
import com.otus.otuskotlin.groschenberry.e2e.be.test.action.cib.toV1

enum class TestDebug {
    STUB, PROD, TEST
}

// Kotest не сможет подставить правильный аргумент конструктора, поэтому
// нужно запретить ему запускать этот класс
@Ignored
open class AccRestTestBaseFull(dockerCompose: DockerCompose, debug: TestDebug = TestDebug.STUB) : BaseFunSpec(dockerCompose, {
    val restClient = RestClient(dockerCompose)
    testApiCIB(restClient, prefix = "rest ", debug = debug.toV1())
})
@Ignored
open class AccRestTestBaseShort(dockerCompose: DockerCompose, debug: TestDebug = TestDebug.STUB) : BaseFunSpec(dockerCompose, {
    val restClient = RestClient(dockerCompose)
    testApiCIB(restClient, prefix = "rest ", debug = debug.toV1())
})

class AccRestWiremockTest : AccRestTestBaseFull(WiremockDockerCompose)

//class AccRestKtorPgJvmTest : AccRestTestBaseFull(KtorJvmPGDockerCompose, debug = TestDebug.PROD)
//class AccRestKtorPgLinuxTest : AccRestTestBaseShort(KtorLinuxPGDockerCompose, debug = TestDebug.PROD)

// В связи с добавлением авторизации, тесты без KeyCloak больше не работают
//class AccRestSpringPgTest : AccRestTestBaseFull(SpringDockerCompose, debug = TestDebug.PROD)
//class AccRestKtorPgJvmTest : AccRestTestBaseFull(KtorJvmPGDockerCompose, debug = TestDebug.PROD)
////class AccRestKtorPgLinuxTest : AccRestTestBaseShort(KtorLinuxPGDockerCompose, debug = TestDebug.PROD)
//class AccRestKtorCsJvmTest : AccRestTestBaseFull(KtorJvmCSDockerCompose, debug = TestDebug.PROD)
//class AccRestKtorGrJvmTest : AccRestTestBaseFull(KtorJvmGRDockerCompose, debug = TestDebug.PROD)

class AccRestKtorKeycloakJvmTest : BaseFunSpec(KtorJvmKeycloakDockerCompose, {
    val restClient = RestAuthClient(KtorJvmKeycloakDockerCompose)
    val debug = TestDebug.TEST
    testApiCIB(restClient, prefix = "rest ", debug = debug.toV1())
})