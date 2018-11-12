package me.hltj.kthumbor.parser

/**
 * return corresponding format for given suffix ([this])
 */
internal fun String.toThumbnailFormat(): String? = when (val lower = toLowerCase()) {
    "jpg" -> "jpeg"
    in setOf("bmp", "png", "gif", "jpeg") -> lower
    else -> null
}
