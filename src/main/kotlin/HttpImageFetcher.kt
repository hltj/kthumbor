package me.hltj.kthumbor

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import io.ktor.client.features.*
import io.ktor.util.*

@UseExperimental(KtorExperimentalAPI::class)
private val httpClient = HttpClient(CIO)

suspend fun httpImageOf(path: String): KthumborResult<BufferedImage> = try {
    val bytes = httpClient.get<ByteArray>("http://localhost:8080$path")

    KthumborResult.Success(ImageIO.read(bytes.inputStream()).withAlpha())
} catch (e: ResponseException) {
    if (e.response.status == HttpStatusCode.NotFound) KthumborResult.NotFound else KthumborResult.BadInput
} catch (e: Exception) {
    KthumborResult.Failure(e)
}
