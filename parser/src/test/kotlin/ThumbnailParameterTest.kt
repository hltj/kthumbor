package me.hltj.kthumbor.parser.test

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import me.hltj.kthumbor.parser.toThumbnailParameter

class ThumbnailParameterTest : StringSpec({
    "1677x9 => w1677 h9" {
        with("1677x9".toThumbnailParameter()!!) {
            width shouldBe 1677
            height shouldBe 9
        }
    }

    "40x30 => w40 h30" {
        with("40x30".toThumbnailParameter()!!) {
            width shouldBe 40
            height shouldBe 30
        }
    }

    "40x030 => null" {
        "40x030".toThumbnailParameter() shouldBe null
    }

    "040x30 => null" {
        "040x30".toThumbnailParameter() shouldBe null
    }

    "40x0 => null" {
        "40x0".toThumbnailParameter() shouldBe null
    }

    "0x30 => null" {
        "0x30".toThumbnailParameter() shouldBe null
    }

    "0x0 => null" {
        "0x0".toThumbnailParameter() shouldBe null
    }

    "40 => w40 h0" {
        with("40".toThumbnailParameter()!!) {
            width shouldBe 40
            height shouldBe 0
        }
    }

    "x30 => w0 h30" {
        with("x30".toThumbnailParameter()!!) {
            width shouldBe 0
            height shouldBe 30
        }
    }

    "x => null" {
        "x".toThumbnailParameter() shouldBe null
    }

    "40X30 => null" {
        "40X30".toThumbnailParameter() shouldBe null
    }

    "123-235 => null" {
        "123-235".toThumbnailParameter() shouldBe null
    }
})