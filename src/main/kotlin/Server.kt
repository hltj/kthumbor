package me.hltj.kthumbor

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import java.util.concurrent.atomic.AtomicLong

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

fun Application.module() {
    install(CallId) {
        header(HttpHeaders.XRequestId)

        val counter = AtomicLong()
        generate { "hltj-me-${counter.incrementAndGet()}" }
    }

    install(CallLogging) {
        callIdMdc("request-id")
    }

    routing {
        get("/") {
            call.respondText("Kthumbor - a thumbnail service")
        }

        static("/") {
            resources("static")
        }
    }
}
