package me.hltj.kthumbor.generator

import me.hltj.kthumbor.share.ThumbnailParameter

import net.coobird.thumbnailator.Thumbnails
import java.awt.image.BufferedImage
import kotlin.math.roundToInt

/**
 * generate a thumbnail with [parameter] for a [BufferedImage]
 */
operator fun BufferedImage.times(parameter: ThumbnailParameter): BufferedImage {
    val w = if (parameter.width > 0) parameter.width else (parameter.height * width / height.toDouble()).roundToInt()
    val h = if (parameter.height > 0) parameter.height else (parameter.width * height / width.toDouble()).roundToInt()

    return if (w > width && h > height) this else Thumbnails.of(this).size(w, h).asBufferedImage()
}
