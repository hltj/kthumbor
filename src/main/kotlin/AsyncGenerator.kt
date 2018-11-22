package me.hltj.kthumbor

import me.hltj.kthumbor.generator.times
import me.hltj.kthumbor.parser.toThumbnailInput
import me.hltj.kthumbor.share.AsyncThumbnailInput
import me.hltj.kthumbor.share.supportAlpha
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.OutputStream
import javax.imageio.ImageIO

sealed class KthumborResult<out T> {
    data class Success<T>(val value: T) : KthumborResult<T>()
    data class Failure(val exception: Exception) : KthumborResult<Nothing>()
    object NotFound : KthumborResult<Nothing>()
    object BadInput : KthumborResult<Nothing>()
}

/**
 * convert a URL path string([this]) with [imageGetter] to [AsyncThumbnailInput] wrapped in [KthumborResult]
 */
suspend infix fun String.fetchWith(
    imageGetter: suspend (String) -> KthumborResult<BufferedImage>
): KthumborResult<AsyncThumbnailInput> = toThumbnailInput()?.let {
    KthumborResult.Success(
        AsyncThumbnailInput(
            parameter = it.parameter,
            format = it.format,
            image = when (val result = imageGetter(it.originPath)) {
                is KthumborResult.Success<BufferedImage> -> result.value
                is KthumborResult.Failure -> return result
                is KthumborResult.BadInput -> return result
                is KthumborResult.NotFound -> return result
            }
        )
    )
} ?: KthumborResult.BadInput

/**
 * append thumbnail specified by [AsyncThumbnailInput]
 */
operator fun OutputStream.plusAssign(input: AsyncThumbnailInput) {
    val image = (input.image * input.parameter).let {
        if (input.format.supportAlpha) it else it.withoutAlpha()
    }
    ImageIO.write(image, input.format.name, this)
}

/**
 * return the same image without alpha channel
 */
internal fun BufferedImage.withoutAlpha(): BufferedImage = withAlpha(false)

/**
 * return the same image with/without alpha channel
 */
internal fun BufferedImage.withAlpha(alpha: Boolean = true): BufferedImage {
    val image = BufferedImage(width, height, if (alpha) BufferedImage.TYPE_INT_ARGB else BufferedImage.TYPE_INT_RGB)
    val g = image.createGraphics()
    if (!alpha) {
        g.color = Color.WHITE
        g.fillRect(0, 0, width, height)
    }
    g.drawImage(this, 0, 0, null)
    g.dispose()
    return image
}