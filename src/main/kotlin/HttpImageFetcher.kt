package me.hltj.kthumbor

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.ResponseException
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

@OptIn(KtorExperimentalAPI::class)
private val httpClient = HttpClient(CIO)

suspend fun httpImageOf(path: String): KthumborResult<BufferedImage> = try {
    val bytes = httpClient.get<ByteArray>("http://localhost:8080$path")
    val image = withContext(Dispatchers.IO) {
        ImageIO.read(bytes.inputStream())
    }
    KthumborResult.Success(image.withAlpha())
} catch (e: ResponseException) {
    if (e.response.status == HttpStatusCode.NotFound) KthumborResult.NotFound else KthumborResult.BadInput
} catch (e: Exception) {
    KthumborResult.Failure(e)
}
