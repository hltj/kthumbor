package me.hltj.kthumbor.parser

data class ThumbnailUrlPath(
    val originPath: String,
    val parameter: String,
    val suffix: String
)

/**
 * parse a URL path string ([this]) to [ThumbnailUrlPath] or `null` if failed
 * e.g.:
 * `"/path/to/image.suffix0.param.suffix"` to `ThumbnailUrlPath("/path/to/image.suffix0", "param", "suffix")`
 */
fun String.toThumbnailUrlPath(): ThumbnailUrlPath? = TODO("not implemented")