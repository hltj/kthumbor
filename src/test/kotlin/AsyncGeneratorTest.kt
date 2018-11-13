package me.hltj.kthumbor.test

import io.kotlintest.TestContext
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import me.hltj.kthumbor.ThumbnailResult
import me.hltj.kthumbor.fetchWith
import me.hltj.kthumbor.share.AsyncThumbnailInput
import me.hltj.kthumbor.share.ThumbnailParameter
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

class AsyncThumbnailInputTest : StringSpec({
    "/oss.png.30x20.jpg" {
        runBlocking {
            with(("/oss.png.30x20.jpg" fetchWith ::staticResourceFetcher)) {
                should {
                    it is ThumbnailResult.Success<AsyncThumbnailInput>
                }

                with((this as ThumbnailResult.Success<AsyncThumbnailInput>).value) {
                    image should {  it equalsTo staticResourceImageOf("/oss.png") }
                    parameter shouldBe ThumbnailParameter(30, 20)
                    format shouldBe "jpeg"
                }
            }
        }
    }

    "/oss.png.0x40.png => BadInput" {
        runBlocking {
            ("/oss.png.0x40.png" fetchWith ::staticResourceFetcher) shouldBe ThumbnailResult.BadInput
        }
    }

    "BadInput => BadInput" {
        runBlocking {
            ("/text.txt.80x80.png" fetchWith { ThumbnailResult.BadInput }) shouldBe ThumbnailResult.BadInput
        }
    }

    "NotFound => NotFound" {
        runBlocking {
            ("no-such-image.80x80.png" fetchWith { ThumbnailResult.NotFound }) shouldBe ThumbnailResult.NotFound
        }
    }

    "Failure => Failure" {
        runBlocking {
            with(("/whatever.80x80.png" fetchWith { ThumbnailResult.Failure(Exception("blah, blah")) })) {
                should {
                    it is ThumbnailResult.Failure
                }

                with((this as ThumbnailResult.Failure)) {
                    exception.message shouldBe "blah, blah"
                }
            }
        }
    }
})

internal suspend fun TestContext.staticResourceFetcher(originPath: String): ThumbnailResult<BufferedImage> {
    delay(0L)
    return ThumbnailResult.Success(staticResourceImageOf(originPath))
}

internal fun TestContext.staticResourceImageOf(originPath: String): BufferedImage = ImageIO.read(staticResourceOf(originPath))

internal infix fun BufferedImage.equalsTo(another: BufferedImage): Boolean {
    return if (width != another.width || height != another.height) {
        false
    } else {
        for (x in 0 until width) {
            for (y in 0 until height) {
                if (getRGB(x, y) != another.getRGB(x, y)) {
                    return false
                }
            }
        }
        true
    }
}
