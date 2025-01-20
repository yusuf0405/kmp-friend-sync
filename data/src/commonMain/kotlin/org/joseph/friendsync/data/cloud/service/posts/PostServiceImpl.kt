package org.joseph.friendsync.data.cloud.service.posts

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.content.PartData
import org.joseph.friendsync.data.di.BASE_URL
import org.joseph.friendsync.data.cloud.models.PostListResponse
import org.joseph.friendsync.data.cloud.models.PostResponse
import org.joseph.friendsync.data.cloud.ADD_REQUEST_PATH
import org.joseph.friendsync.data.cloud.LIST_REQUEST_PATH
import org.joseph.friendsync.data.cloud.MESSAGE_PARAM
import org.joseph.friendsync.data.cloud.PAGE_PARAM
import org.joseph.friendsync.data.cloud.PAGE_SIZE_PARAM
import org.joseph.friendsync.data.cloud.POST_REQUEST_PATH
import org.joseph.friendsync.data.cloud.QUERY_PARAM
import org.joseph.friendsync.data.cloud.RECOMMENDED_REQUEST_PATH
import org.joseph.friendsync.data.cloud.SEARCH_REQUEST_PATCH
import org.joseph.friendsync.data.cloud.USER_ID_PARAM

/**
 * Реализация сервиса для управления постами через HTTP-клиент.
 *
 * Класс предоставляет методы для взаимодействия с API постов.
 *
 * @property client HTTP-клиент для отправки запросов к API.
 */
internal class PostServiceImpl(private val client: HttpClient) : PostService {

    override suspend fun fetchUserPosts(userId: Int): PostListResponse {
        return client.get("$POST_REQUEST_PATH$LIST_REQUEST_PATH/${userId}").body()
    }

    override suspend fun fetchPostById(postId: Int): PostResponse {
        return client.get("$POST_REQUEST_PATH/${postId}").body()
    }

    override suspend fun fetchRecommendedPosts(
        page: Int,
        pageSize: Int,
        userId: Int
    ): PostListResponse = client.get("$POST_REQUEST_PATH$RECOMMENDED_REQUEST_PATH") {
        parameter(PAGE_PARAM, page)
        parameter(PAGE_SIZE_PARAM, pageSize)
        parameter(USER_ID_PARAM, userId)
    }.body()

    override suspend fun searchPosts(
        query: String,
        page: Int,
        pageSize: Int
    ): PostListResponse = client.get("$POST_REQUEST_PATH$SEARCH_REQUEST_PATCH") {
        parameter(PAGE_PARAM, page)
        parameter(PAGE_SIZE_PARAM, pageSize)
        parameter(QUERY_PARAM, query)
    }.body()

    override suspend fun addPost(
        byteArrays: List<ByteArray?>,
        message: String?,
        userId: Int
    ): PostResponse = client.submitFormWithBinaryData(
        url = "$BASE_URL$POST_REQUEST_PATH$ADD_REQUEST_PATH",
        formData = createFormData(byteArrays, message, userId)
    ) {
        onUpload { bytesSentTotal, contentLength ->
            Napier.i("Sent $bytesSentTotal bytes from $contentLength")
        }
    }.body()

    private fun createFormData(
        byteArrays: List<ByteArray?>,
        message: String?,
        userId: Int
    ): List<PartData> = formData {
        append(USER_ID_PARAM, userId.toString())
        message?.let { append(MESSAGE_PARAM, it) }
        byteArrays.filterNotNull().forEachIndexed { index, byteArray ->
            append("image$index", byteArray, Headers.build {
                append(HttpHeaders.ContentType, ContentType.Image.PNG.toString())
                append(HttpHeaders.ContentDisposition, "filename=image$index.png")
            })
        }
    }
}
