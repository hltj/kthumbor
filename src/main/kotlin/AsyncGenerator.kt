package me.hltj.kthumbor

import me.hltj.kthumbor.parser.toThumbnailInput
import me.hltj.kthumbor.share.AsyncThumbnailInput
import java.awt.image.BufferedImage

sealed class ThumbnailResult<out T> {
    data class Success<T>(val value: T) : ThumbnailResult<T>()
    data class Failure(val exception: Exception) : ThumbnailResult<Nothing>()
    object NotFound : ThumbnailResult<Nothing>()
    object BadInput : ThumbnailResult<Nothing>()
}

/**
 * convert a URL path string([this]) with [imageGetter] to [AsyncThumbnailInput] wrapped in [ThumbnailResult]
 */
suspend infix fun String.fetchWith(
    imageGetter: suspend (String) -> ThumbnailResult<BufferedImage>
): ThumbnailResult<AsyncThumbnailInput> = toThumbnailInput()?.let {
    ThumbnailResult.Success(
        AsyncThumbnailInput(
            parameter = it.parameter,
            format = it.format,
            image = when (val result = imageGetter(it.originPath)) {
                is ThumbnailResult.Success<BufferedImage> -> result.value
                is ThumbnailResult.Failure -> return result
                is ThumbnailResult.BadInput -> return result
                is ThumbnailResult.NotFound -> return result
            }
        )
    )
} ?: ThumbnailResult.BadInput
