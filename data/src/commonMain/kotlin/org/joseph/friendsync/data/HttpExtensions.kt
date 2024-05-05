package org.joseph.friendsync.data

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.path
import kotlinx.coroutines.CancellationException
import org.joseph.friendsync.core.Result

suspend inline fun <reified T> HttpClient.request(
    endPoint: String,
    crossinline block: HttpRequestBuilder.() -> Unit
): Result<T> = try {
    val requestBlock: HttpRequestBuilder.() -> Unit = {
        block.invoke(this)
        url { path(endPoint) }
    }
    val response = this.request(requestBlock)
    if (response.status == HttpStatusCode.OK) {
        Result.Success(response.body())
    } else {
        val errorMessage = "${response.status}: ${response.bodyAsText()}"
        Napier.e(errorMessage)
        Result.defaultError()
    }
} catch (e: CancellationException) {
    throw e
} catch (e: Exception) {
    Napier.e(e) { e.stackTraceToString() }
    Result.defaultError()
}