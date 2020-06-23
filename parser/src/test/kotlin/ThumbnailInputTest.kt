package me.hltj.kthumbor.parser.test

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import me.hltj.kthumbor.parser.toThumbnailInput
import me.hltj.kthumbor.share.ThumbnailFormat
import me.hltj.kthumbor.share.ThumbnailParameter

class ThumbnailInputTest : StringSpec({
    "/oss.png.80x80.png" {
        with("/oss.png.80x80.png".toThumbnailInput()!!) {
            originPath shouldBe "/oss.png"
            parameter shouldBe ThumbnailParameter(80, 80)
            format shouldBe ThumbnailFormat("png")
        }
    }

    "/oss.png.60.jpg" {
        with("/oss.png.60.jpg".toThumbnailInput()!!) {
            originPath shouldBe "/oss.png"
            parameter shouldBe ThumbnailParameter(60, 0)
            format shouldBe ThumbnailFormat("jpeg")
        }
    }

    "/oss.png.x40.gif" {
        with("/oss.png.x40.gif".toThumbnailInput()!!) {
            originPath shouldBe "/oss.png"
            parameter shouldBe ThumbnailParameter(0, 40)
            format shouldBe ThumbnailFormat("gif")
        }
    }

    "/oss.png.30x20.BMP" {
        with("/oss.png.30x20.bmp".toThumbnailInput()!!) {
            originPath shouldBe "/oss.png"
            parameter shouldBe ThumbnailParameter(30, 20)
            format shouldBe ThumbnailFormat("bmp")
        }
    }

    "/oss.png.800x900e.png" {
        with("/oss.png.800x900e.png".toThumbnailInput()!!) {
            originPath shouldBe "/oss.png"
            parameter shouldBe ThumbnailParameter(800, 900, enlargeable = true)
            format shouldBe ThumbnailFormat("png")
        }
    }

    "/oss.png.0x0.png => null" {
        "/path/to/image.0x0.png".toThumbnailInput() shouldBe null
    }

    ".80x60.png => null" {
        ".80x60.png".toThumbnailInput() shouldBe null
    }

    "/oss.png.80x60.txt => null" {
        "/oss.png.80x60.txt".toThumbnailInput() shouldBe null
    }
})