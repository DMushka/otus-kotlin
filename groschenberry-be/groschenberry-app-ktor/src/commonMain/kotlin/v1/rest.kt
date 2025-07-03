package com.otus.otuskotlin.groschenberry.app.ktor.v1

import io.ktor.server.routing.*
import com.otus.otuskotlin.groschenberry.app.ktor.GrschbrAppSettings
import io.ktor.server.application.call
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.ci(appSettings: GrschbrAppSettings) {

    route("basic") {

        post("create") {
            call.createCIB(appSettings)
        }
        post("read") {
            call.readCIB(appSettings)
        }
        post("update") {
            call.updateCIB(appSettings)
        }
        post("delete") {
            call.deleteCIB(appSettings)
        }
        post("search") {
            call.searchCIB(appSettings)
        }
    }

    route("detail") {
        post("create") {
            call.createCID(appSettings)
        }
        post("read") {
            call.readCID(appSettings)
        }
        post("update") {
            call.updateCID(appSettings)
        }
        post("delete") {
            call.deleteCID(appSettings)
        }
        post("search") {
            call.searchCID(appSettings)
        }
    }
}