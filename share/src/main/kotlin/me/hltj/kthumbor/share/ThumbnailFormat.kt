package me.hltj.kthumbor.share

@JvmInline
value class ThumbnailFormat(val name: String)

/**
 * return corresponding format for given suffix ([this])
 */
fun String.toThumbnailFormat(): ThumbnailFormat? = when (val lower = lowercase()) {
    "jpg" -> ThumbnailFormat("jpeg")
    in setOf("bmp", "png", "gif", "jpeg") -> ThumbnailFormat(lower)
    else -> null
}

val ThumbnailFormat.supportAlpha get() = name in setOf("png", "gif")
