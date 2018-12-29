package me.hltj.kthumbor.generator.test

import me.hltj.kthumbor.share.ThumbnailParameter
import me.hltj.kthumbor.generator.times

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.awt.image.BufferedImage

class ThumbnailGeneratorTest : StringSpec({
    val squareImage = BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB)
    val bannerImage = BufferedImage(800, 320, BufferedImage.TYPE_INT_ARGB)

    "square * 20x20 => 20x20" {
        with(squareImage * ThumbnailParameter(20, 20)) {
            width shouldBe 20
            height shouldBe 20
        }
    }

    "banner * 20x20 => 20 x 8" {
        with(bannerImage * ThumbnailParameter(20, 20)) {
            width shouldBe 20
            height shouldBe 8
        }
    }

    "square * 40x30 => 30x30" {
        with(squareImage * ThumbnailParameter(40, 30)) {
            width shouldBe 30
            height shouldBe 30
        }
    }

    "square * 30x40 => 30x30" {
        with(squareImage * ThumbnailParameter(30, 40)) {
            width shouldBe 30
            height shouldBe 30
        }
    }

    "banner * 80x20 => 50x20" {
        with(bannerImage * ThumbnailParameter(80, 20)) {
            width shouldBe 50
            height shouldBe 20
        }
    }

    "square * 50x0 => 50x50" {
        with(squareImage * ThumbnailParameter(50, 0)) {
            width shouldBe 50
            height shouldBe 50
        }
    }

    "square * 0x50 => 50x50" {
        with(squareImage * ThumbnailParameter(0, 50)) {
            width shouldBe 50
            height shouldBe 50
        }
    }

    "banner * 80x0 => 80x32" {
        with(bannerImage * ThumbnailParameter(80, 0)) {
            width shouldBe 80
            height shouldBe 32
        }
    }

    "banner * 0x40 => 100x40" {
        with(bannerImage * ThumbnailParameter(0, 40)) {
            width shouldBe 100
            height shouldBe 40
        }
    }


    "square * 1000x100 => 100x100" {
        with(squareImage * ThumbnailParameter(1000, 100)) {
            width shouldBe 100
            height shouldBe 100
        }
    }

    "square * 1000x1000 => 500x500" {
        with(squareImage * ThumbnailParameter(1000, 1000)) {
            width shouldBe 500
            height shouldBe 500
        }
    }

    "square * 1000x0 => 500x500" {
        with(squareImage * ThumbnailParameter(1000, 0)) {
            width shouldBe 500
            height shouldBe 500
        }
    }

    "square * 1000x1000-e => 1000x1000" {
        with(squareImage * ThumbnailParameter(1000, 1000, enlargeable = true)) {
            width shouldBe 1000
            height shouldBe 1000
        }
    }

    "square * x1000-e => 1000x1000" {
        with(squareImage * ThumbnailParameter(0, 1000, enlargeable = true)) {
            width shouldBe 1000
            height shouldBe 1000
        }
    }

    "square * 1000x800-e => 800x800" {
        with(squareImage * ThumbnailParameter(1000, 800, enlargeable = true)) {
            width shouldBe 800
            height shouldBe 800
        }
    }


    "square * 200x1000-e => 200x200" {
        with(squareImage * ThumbnailParameter(200, 1000, enlargeable = true)) {
            width shouldBe 200
            height shouldBe 200
        }
    }
})