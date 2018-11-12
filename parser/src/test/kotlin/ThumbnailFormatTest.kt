package me.hltj.kthumbor.parser.test

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import me.hltj.kthumbor.parser.toThumbnailFormat

class ThumbnailFormatTest : StringSpec({
    "bmp" {
        "bmp".toThumbnailFormat() shouldBe "bmp"
        "BMP".toThumbnailFormat() shouldBe "bmp"
    }

    "png" {
        "png".toThumbnailFormat() shouldBe "png"
        "PNG".toThumbnailFormat() shouldBe "png"
    }

    "gif" {
        "gif".toThumbnailFormat() shouldBe "gif"
        "GIF".toThumbnailFormat() shouldBe  "gif"
    }

    "jpeg" {
        "jpg".toThumbnailFormat() shouldBe "jpeg"
        "JPG".toThumbnailFormat() shouldBe "jpeg"
        "jpeg".toThumbnailFormat() shouldBe "jpeg"
        "JPEG".toThumbnailFormat() shouldBe "jpeg"
    }

    "other => null" {
        "txt".toThumbnailFormat() shouldBe null
        "HTML".toThumbnailFormat() shouldBe null
        "cc".toThumbnailFormat() shouldBe null
        "kt".toThumbnailFormat() shouldBe null
    }
})