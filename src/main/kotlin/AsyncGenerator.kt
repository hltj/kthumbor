package me.hltj.kthumbor

import kotlinx.coroutines.delay
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
): ThumbnailResult<AsyncThumbnailInput> {
    delay(0L)
    TODO("not implemented")
}
