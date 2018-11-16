package me.hltj.kthumbor.test

import io.kotlintest.TestContext
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import me.hltj.kthumbor.KthumborResult
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
                    it is KthumborResult.Success<AsyncThumbnailInput>
                }

                with((this as KthumborResult.Success<AsyncThumbnailInput>).value) {
                    image should {  it equalsTo staticResourceImageOf("/oss.png") }
                    parameter shouldBe ThumbnailParameter(30, 20)
                    format shouldBe "jpeg"
                }
            }
        }
    }

    "/oss.png.0x40.png => BadInput" {
        runBlocking {
            ("/oss.png.0x40.png" fetchWith ::staticResourceFetcher) shouldBe KthumborResult.BadInput
        }
    }

    "BadInput => BadInput" {
        runBlocking {
            ("/text.txt.80x80.png" fetchWith { KthumborResult.BadInput }) shouldBe KthumborResult.BadInput
        }
    }

    "NotFound => NotFound" {
        runBlocking {
            ("no-such-image.80x80.png" fetchWith { KthumborResult.NotFound }) shouldBe KthumborResult.NotFound
        }
    }

    "Failure => Failure" {
        runBlocking {
            with(("/whatever.80x80.png" fetchWith { KthumborResult.Failure(Exception("blah, blah")) })) {
                should {
                    it is KthumborResult.Failure
                }

                with((this as KthumborResult.Failure)) {
                    exception.message shouldBe "blah, blah"
                }
            }
        }
    }
})

internal suspend fun TestContext.staticResourceFetcher(originPath: String): KthumborResult<BufferedImage> {
    delay(0L)
    return KthumborResult.Success(staticResourceImageOf(originPath))
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
