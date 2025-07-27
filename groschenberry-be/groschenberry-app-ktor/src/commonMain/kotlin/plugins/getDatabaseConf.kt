package com.otus.otuskotlin.groschenberry.app.ktor.plugins

import io.ktor.server.application.*
import com.otus.otuskotlin.groschenberry.common.repo.IRepoCI
import com.otus.otuskotlin.groschenberry.repo.inmemory.CIRepoInMemory
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

expect fun Application.getDatabaseConf(type: CIDbType): IRepoCI

enum class CIDbType(val confName: String) {
    PROD("prod"), TEST("test")
}

fun Application.initInMemory(): IRepoCI {
    val ttlSetting = environment.config.propertyOrNull("db.prod")?.getString()?.let {
        Duration.parse(it)
    }
    return CIRepoInMemory(ttl = ttlSetting ?: 10.minutes)
}
