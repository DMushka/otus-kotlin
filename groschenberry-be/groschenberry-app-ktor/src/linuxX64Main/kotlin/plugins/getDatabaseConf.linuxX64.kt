package com.otus.otuskotlin.groschenberry.app.ktor.plugins

import io.ktor.server.application.*
import com.otus.otuskotlin.groschenberry.app.ktor.configs.ConfigPaths
import com.otus.otuskotlin.groschenberry.common.repo.IRepoCI

actual fun Application.getDatabaseConf(type: CIDbType): IRepoCI {
    val dbSettingPath = "${ConfigPaths.repository}.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    return when (dbSetting) {
        "in-memory", "inmemory", "memory", "mem" -> initInMemory()
//        "postgres", "postgresql", "pg", "sql", "psql" -> initPostgres()
        else -> throw IllegalArgumentException(
            "$dbSettingPath must be set in application.yml to one of: " +
                    "'inmemory'"
        )
    }
}

//fun Application.initPostgres(): IRepoCI {
//    val config = PostgresConfig(environment.config)
//    return RepoCISql(
//        properties = SqlProperties(
//            host = config.host,
//            port = config.port,
//            user = config.user,
//            password = config.password,
//            schema = config.schema,
//            database = config.database,
//        ),
//    )
//}
