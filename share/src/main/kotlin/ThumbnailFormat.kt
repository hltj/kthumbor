package me.hltj.kthumbor.share

inline class ThumbnailFormat(val name: String)

/**
 * return corresponding format for given suffix ([this])
 */
fun String.toThumbnailFormat(): ThumbnailFormat? = when (val lower = toLowerCase()) {
    "jpg" -> ThumbnailFormat("jpeg")
    in setOf("bmp", "png", "gif", "jpeg") -> ThumbnailFormat(lower)
    else -> null
}

val ThumbnailFormat.supportAlpha get() = name in setOf("png", "gif")
