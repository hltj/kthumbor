package me.hltj.kthumbor.parser.test

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import me.hltj.kthumbor.parser.toThumbnailParameter

class ThumbnailParameterBasicTest : StringSpec({
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

class ThumbnailParameterEnlargeableTest: StringSpec({
    "40x30 => w40 h30 non-enlargeable" {
        with("40x30".toThumbnailParameter()!!) {
            width shouldBe 40
            height shouldBe 30
            enlargeable shouldBe false
        }
    }

    "40x30e => w40 h30 enlargeable" {
        with("40x30e".toThumbnailParameter()!!) {
            width shouldBe 40
            height shouldBe 30
            enlargeable shouldBe true
        }
    }

    "40 => w40 non-enlargeable" {
        with("40".toThumbnailParameter()!!) {
            width shouldBe 40
            height shouldBe 0
            enlargeable shouldBe false
        }
    }

    "40e => w40enlargeable" {
        with("40e".toThumbnailParameter()!!) {
            width shouldBe 40
            height shouldBe 0
            enlargeable shouldBe true
        }
    }

    "x30 => h30 non-enlargeable" {
        with("x30".toThumbnailParameter()!!) {
            width shouldBe 0
            height shouldBe 30
            enlargeable shouldBe false
        }
    }

    "x30e => h30 enlargeable" {
        with("x30e".toThumbnailParameter()!!) {
            width shouldBe 0
            height shouldBe 30
            enlargeable shouldBe true
        }
    }


    "40x030e => null" {
        "40x030e".toThumbnailParameter() shouldBe null
    }

    "040x30e => null" {
        "040x30e".toThumbnailParameter() shouldBe null
    }

    "40x0e => null" {
        "40x0e".toThumbnailParameter() shouldBe null
    }

    "0x30e => null" {
        "0x30e".toThumbnailParameter() shouldBe null
    }

    "0x0e => null" {
        "0x0e".toThumbnailParameter() shouldBe null
    }

    "xe => null" {
        "xe".toThumbnailParameter() shouldBe null
    }

    "40x30E => null" {
        "40x30E".toThumbnailParameter() shouldBe null
    }
})