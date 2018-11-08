package me.hltj.kthumbor.parser.test

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import me.hltj.kthumbor.parser.ThumbnailUrlPath
import me.hltj.kthumbor.parser.toThumbnailUrlPath

class ThumbnailUrlPathTest : StringSpec({
    "typical" {
        "/path/to/image.suffix0.param.suffix".toThumbnailUrlPath() shouldBe ThumbnailUrlPath(
            originPath = "/path/to/image.suffix0",
            parameter = "param",
            suffix = "suffix"
        )
    }

    "no suffix in origin path" {
        "/path/to/image.param.suffix".toThumbnailUrlPath() shouldBe ThumbnailUrlPath(
            originPath = "/path/to/image",
            parameter = "param",
            suffix = "suffix"
        )
    }

    "multiple dots in origin path" {
        "/path/to/image.0.1.2.param.suffix".toThumbnailUrlPath() shouldBe ThumbnailUrlPath(
            originPath = "/path/to/image.0.1.2",
            parameter = "param",
            suffix = "suffix"
        )
    }

    "no suffix => null" {
        "path-to-image.param.".toThumbnailUrlPath() shouldBe null
    }

    "no param => null" {
        "image..suffix".toThumbnailUrlPath() shouldBe null
    }

    "no origin path => null" {
        ".param.suffix".toThumbnailUrlPath() shouldBe null
    }
})
