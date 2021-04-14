package me.hltj.kthumbor.share.test

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import me.hltj.kthumbor.share.ThumbnailFormat
import me.hltj.kthumbor.share.supportAlpha
import me.hltj.kthumbor.share.toThumbnailFormat

class ThumbnailFormatTest : StringSpec({
    "bmp" {
        "bmp".toThumbnailFormat() shouldBe ThumbnailFormat("bmp")
        "BMP".toThumbnailFormat() shouldBe ThumbnailFormat("bmp")
    }

    "png" {
        "png".toThumbnailFormat() shouldBe ThumbnailFormat("png")
        "PNG".toThumbnailFormat() shouldBe ThumbnailFormat("png")
    }

    "gif" {
        "gif".toThumbnailFormat() shouldBe ThumbnailFormat("gif")
        "GIF".toThumbnailFormat() shouldBe ThumbnailFormat("gif")
    }

    "jpeg" {
        "jpg".toThumbnailFormat() shouldBe ThumbnailFormat("jpeg")
        "JPG".toThumbnailFormat() shouldBe ThumbnailFormat("jpeg")
        "jpeg".toThumbnailFormat() shouldBe ThumbnailFormat("jpeg")
        "JPEG".toThumbnailFormat() shouldBe ThumbnailFormat("jpeg")
    }

    "other => null" {
        "txt".toThumbnailFormat() shouldBe null
        "HTML".toThumbnailFormat() shouldBe null
        "cc".toThumbnailFormat() shouldBe null
        "kt".toThumbnailFormat() shouldBe null
    }

    "support alpha" {
        "bmp".toThumbnailFormat()?.supportAlpha shouldBe false
        "png".toThumbnailFormat()?.supportAlpha shouldBe true
        "gif".toThumbnailFormat()?.supportAlpha shouldBe true
        "jpg".toThumbnailFormat()?.supportAlpha shouldBe false
    }
})