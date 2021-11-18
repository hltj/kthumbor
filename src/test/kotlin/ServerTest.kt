package me.hltj.kthumbor.test

import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestContext
import io.kotest.matchers.shouldBe
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import me.hltj.kthumbor.module
import java.io.ByteArrayInputStream
import java.net.URL
import javax.imageio.ImageIO
import kotlin.concurrent.thread

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

class ThumbnailsTest : StringSpec({
    "200 /oss.png" {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/oss.png").response) {
                status() shouldBe HttpStatusCode.OK
                byteContent shouldBeSameImageAs staticResourceOf("/oss.png")
            }
        }
    }

    "200 /oss.png.80x80.png" {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/oss.png.80x80.png").response) {
                status() shouldBe HttpStatusCode.OK
                byteContent shouldBeSameImageAs staticResourceOf("/oss.png.80x80.png")
            }
        }
    }

    "200 /oss.png.60.jpg" {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/oss.png.60.jpg").response) {
                status() shouldBe HttpStatusCode.OK
                byteContent shouldBeSameImageAs staticResourceOf("/oss.png.60x60.jpg")
            }
        }
    }

    "200 /oss.png.x40.gif" {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/oss.png.x40.gif").response) {
                status() shouldBe HttpStatusCode.OK
                byteContent shouldBeSameImageAs staticResourceOf("/oss.png.40x40.gif")
            }
        }
    }

    "200 /oss.png.30x20.bmp" {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/oss.png.30x20.bmp").response) {
                status() shouldBe HttpStatusCode.OK
                byteContent shouldBeSameImageAs staticResourceOf("/oss.png.20x20.bmp")
            }
        }
    }

    "200 /oss.png.800x800e.jpg" {
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Get, "/oss.png.800x800e.jpg").response) {
                status() shouldBe HttpStatusCode.OK
                byteContent shouldBeSameImageAs staticResourceOf("/oss.png.800x800e.jpg")
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
        thread {
            io.ktor.server.cio.EngineMain.main(emptyArray())
        }
        super.beforeSpec(spec)
    }
}

internal fun TestContext.staticResourceOf(relativePath: String) =
    this::class.java.getResource("/static-test$relativePath")!!

private fun ByteArray?.imageDataBuffer() = ImageIO.read(ByteArrayInputStream(this)).data.dataBuffer

private infix fun ByteArray?.shouldBeSameImageAs(url: URL) {
    val actualBuffer = imageDataBuffer()
    val expectedBuffer = url.readBytes().imageDataBuffer()
    actualBuffer.size shouldBe expectedBuffer.size
    (1 until actualBuffer.size).forEach {
        actualBuffer.getElem(it) shouldBe expectedBuffer.getElem(it)
    }
}