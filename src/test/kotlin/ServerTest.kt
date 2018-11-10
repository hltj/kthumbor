package me.hltj.kthumbor.test

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import me.hltj.kthumbor.module

class ServerTest : StringSpec({
    "200 /" {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/").response) {
                status() shouldBe HttpStatusCode.OK
                content shouldBe "Kthumbor - a thumbnail service"
            }
        }
    }

    "200 favicon" {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/favicon.ico").response) {
                status() shouldBe HttpStatusCode.OK
                byteContent shouldBe this::class.java.getResource("/static/favicon.ico").readBytes()
            }
        }

    }
})