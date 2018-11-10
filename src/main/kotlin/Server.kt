package me.hltj.kthumbor

import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

fun Application.module() {
    routing {
        get("/") {
            call.respondText("Kthumbor - a thumbnail service")
        }

        static("/") {
            resources("static")
        }
    }
}
