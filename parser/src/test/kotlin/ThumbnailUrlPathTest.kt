package me.hltj.kthumbor.parser.test

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import me.hltj.kthumbor.parser.ThumbnailUrlPath
import me.hltj.kthumbor.parser.splitFromEnd
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

class SplitFromEnd : StringSpec({
    "a-b => [a, b]" {
        "a-b".splitFromEnd('-') shouldBe listOf("a", "b")
    }

    "a- => [a, ]" {
        "a-".splitFromEnd('-') shouldBe listOf("a", "")
    }

    "-b => [, b]" {
        "-b".splitFromEnd('-') shouldBe listOf("", "b")
    }

    "a-b-c, ? => [a, b, c]" {
        val s = "a-b-c"
        val list = listOf("a", "b", "c")
        s.splitFromEnd('-') shouldBe list
        s.splitFromEnd('-', 10) shouldBe list
        s.splitFromEnd('-', 3) shouldBe list
        s.splitFromEnd('-', 2) shouldBe list
    }

    "a-bc, ? => [a, bc]" {
        val s = "a-bc"
        val list = listOf("a", "bc")
        s.splitFromEnd('-') shouldBe list
        s.splitFromEnd('-', 10) shouldBe list
        s.splitFromEnd('-', 3) shouldBe list
        s.splitFromEnd('-', 2) shouldBe list
        s.splitFromEnd('-', 1) shouldBe list
    }

    "a-b-c, 1 => [a-b, c]" {
        "a-b-c".splitFromEnd('-', 1) shouldBe listOf("a-b", "c")
    }

    "abc, ? => [abc]" {
        val s = "abc"
        val list = listOf(s)
        s.splitFromEnd('-') shouldBe list
        s.splitFromEnd('-', 10) shouldBe list
        s.splitFromEnd('-', 2) shouldBe list
        s.splitFromEnd('-', 1) shouldBe list
    }

    "123-4-56789, ? => [123, 4, 56789]" {
        val s = "123-4-56789"
        val list = listOf("123", "4", "56789")
        s.splitFromEnd('-') shouldBe list
        s.splitFromEnd('-', 10) shouldBe list
        s.splitFromEnd('-', 2) shouldBe list
    }

    "123-4-56789, 1 => [123-4, 56789]" {
        "123-4-56789".splitFromEnd('-', 1) shouldBe listOf("123-4", "56789")
    }

    "----, ? => [, , , ,]" {
        val list = listOf("", "", "", "", "")
        "----".splitFromEnd('-') shouldBe list
        "----".splitFromEnd('-', 10) shouldBe list
        "----".splitFromEnd('-', 5) shouldBe list
        "----".splitFromEnd('-', 4) shouldBe list
    }

    "----, 3 => [-, , , ]" {
        "----".splitFromEnd('-', 3) shouldBe listOf("-", "", "", "")
    }

    "----, 2 => [--, , ]" {
        "----".splitFromEnd('-', 2) shouldBe listOf("--", "", "")
    }

    "----, 1 => [---, ]" {
        "----".splitFromEnd('-', 1) shouldBe listOf("---", "")
    }

    "----, ? => [----]" {
        "----".splitFromEnd('-', 0) shouldBe listOf("----")
        "----".splitFromEnd('-', -1) shouldBe listOf("----")
        "----".splitFromEnd('-', -9) shouldBe listOf("----")
    }
})