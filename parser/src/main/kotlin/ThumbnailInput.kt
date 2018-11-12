package me.hltj.kthumbor.parser

import me.hltj.kthumbor.share.ThumbnailParameter

data class ThumbnailInput(
    val originPath: String,
    val parameter: ThumbnailParameter,
    val format: String
)

/**
 * parse a URL path string ([this]) to [ThumbnailInput] or `null` if failed
 */
fun String.toThumbnailInput(): ThumbnailInput? = TODO("not implemented")