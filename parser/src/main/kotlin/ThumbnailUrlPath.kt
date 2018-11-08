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
fun String.toThumbnailUrlPath(): ThumbnailUrlPath? {
    val list = splitFromEnd('.', 2)
    return if (list.size != 3 || list.any { it.isEmpty() }) {
        null
    } else {
        ThumbnailUrlPath(list[0], list[1], list[2])
    }
}

internal fun String.splitFromEnd(delimiter: Char, limit: Int = length): List<String> = TODO("not implemented")
