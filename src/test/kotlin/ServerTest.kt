package me.hltj.kthumbor.test

import io.kotlintest.*
import io.kotlintest.specs.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.coroutines.*
import me.hltj.kthumbor.module

class BasicTest : StringSpec({
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

class ThumbnailsTest: StringSpec({
    "200 /oss.png.80x80.png" {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/oss.png.80x80.png").response) {
                status() shouldBe HttpStatusCode.OK
                byteContent shouldBe staticResourceOf("/oss.png.80x80.png").readBytes()
            }
        }
    }

    "200 /oss.png.60.jpg" {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/oss.png.60.jpg").response) {
                status() shouldBe  HttpStatusCode.OK
                byteContent shouldBe staticResourceOf("/oss.png.60x60.jpg").readBytes()
            }
        }
    }

    "200 /oss.png.x40.gif" {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/oss.png.x40.gif").response) {
                status() shouldBe HttpStatusCode.OK
                byteContent shouldBe staticResourceOf("/oss.png.40x40.gif").readBytes()
            }
        }
    }

    "200 /oss.png.30x20.bmp" {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/oss.png.30x20.bmp").response) {
                status() shouldBe HttpStatusCode.OK
                byteContent shouldBe staticResourceOf("/oss.png.20x20.bmp").readBytes()
            }
        }
    }

    "200 /oss.png.800x800e.jpg" {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/oss.png.800x800e.jpg").response) {
                status() shouldBe HttpStatusCode.OK
                byteContent shouldBe staticResourceOf("/oss.png.800x800e.jpg").readBytes()
            }
        }
    }

    "400 /oss.png.not-param.png" {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/oss.png.notparam.png").response) {
                status() shouldBe HttpStatusCode.BadRequest
            }
        }
    }

    "400 /oss.png.80x30.txt" {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/oss.png.80x30.txt").response) {
                status() shouldBe HttpStatusCode.BadRequest
            }
        }
    }

    "404 /no-such-image.80x30.png" {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/no-such-image.80x30.png").response) {
                status() shouldBe HttpStatusCode.NotFound
            }
        }
    }
}) {
    override fun beforeSpec(spec: Spec) {
        GlobalScope.launch {
            io.ktor.server.cio.EngineMain.main(emptyArray())
        }
        super.beforeSpec(spec)
    }
}

internal fun TestContext.staticResourceOf(relativePath: String) =
    this::class.java.getResource("/static-test$relativePath")
