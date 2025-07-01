package com.otus.otuskotlin.groschenberry.app.ktor

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.otus.otuskotlin.groschenberry.api.v1.apiV1Mapper
import com.otus.otuskotlin.groschenberry.app.ktor.plugins.initAppSettings
import com.otus.otuskotlin.groschenberry.app.ktor.v1.ciBasic
import com.otus.otuskotlin.groschenberry.app.ktor.v1.ciDetail

fun Application.module(
    appSettings: GrschbrAppSettings = initAppSettings()
) {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        allowCredentials = true
        /* TODO
            Это временное решение, оно опасно.
            В боевом приложении здесь должны быть конкретные настройки
        */
        anyHost()
    }

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
        route("ci/basic") {
            install(ContentNegotiation) {
                json(apiV1Mapper)
            }
            ciBasic(appSettings)
        }
        route("ci/detail") {
            install(ContentNegotiation) {
                json(apiV1Mapper)
            }
            ciDetail(appSettings)
        }
    }
}
