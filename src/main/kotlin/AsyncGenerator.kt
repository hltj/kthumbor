package me.hltj.kthumbor

import me.hltj.kthumbor.generator.times
import me.hltj.kthumbor.parser.toThumbnailInput
import me.hltj.kthumbor.share.AsyncThumbnailInput
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
    ImageIO.write(input.image * input.parameter, input.format, this)
}
