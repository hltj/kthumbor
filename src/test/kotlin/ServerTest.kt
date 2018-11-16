package me.hltj.kthumbor.test

import io.kotlintest.*
import io.kotlintest.specs.*
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

    "200 static" {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/favicon.ico").response) {
                status() shouldBe HttpStatusCode.OK
                byteContent shouldBe staticResourceOf("/favicon.ico").readBytes()
            }

            with(handleRequest(HttpMethod.Get, "/oss.png").response) {
                status() shouldBe HttpStatusCode.OK
                byteContent shouldBe staticResourceOf("/oss.png").readBytes()
            }
        }
    }
})

internal fun TestContext.staticResourceOf(relativePath: String) =
    this::class.java.getResource("/static-test$relativePath")
