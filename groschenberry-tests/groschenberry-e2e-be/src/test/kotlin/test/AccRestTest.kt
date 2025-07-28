package com.otus.otuskotlin.groschenberry.e2e.be.test

import io.kotest.core.annotation.Ignored
import com.otus.otuskotlin.groschenberry.blackbox.fixture.docker.DockerCompose
import com.otus.otuskotlin.groschenberry.e2e.be.docker.KtorJvmPGDockerCompose
import com.otus.otuskotlin.groschenberry.e2e.be.docker.WiremockDockerCompose
import com.otus.otuskotlin.groschenberry.e2e.be.fixture.BaseFunSpec
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
