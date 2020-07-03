package me.hltj.kthumbor.share

import java.awt.image.BufferedImage

data class AsyncThumbnailInput(
    val image: BufferedImage,
    val parameter: ThumbnailParameter,
    val format: ThumbnailFormat
)