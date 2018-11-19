package me.hltj.kthumbor.parser

import me.hltj.kthumbor.share.ThumbnailFormat
import me.hltj.kthumbor.share.ThumbnailParameter
import me.hltj.kthumbor.share.toThumbnailFormat

data class ThumbnailInput(
    val originPath: String,
    val parameter: ThumbnailParameter,
    val format: ThumbnailFormat
)

/**
 * parse a URL path string ([this]) to [ThumbnailInput] or `null` if failed
 */
fun String.toThumbnailInput(): ThumbnailInput? = toThumbnailUrlPath()?.let {
    ThumbnailInput(
        originPath = it.originPath,
        parameter = it.parameter.toThumbnailParameter() ?: return null,
        format = it.suffix.toThumbnailFormat() ?: return null
    )
}
