package org.joseph.friendsync.data.service

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.parameter
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.data.di.BASE_URL
import org.joseph.friendsync.data.models.post.PostListResponse
import org.joseph.friendsync.data.models.post.PostResponse
import org.joseph.friendsync.data.models.post.RecommendedPostsParam
import org.joseph.friendsync.data.request

private const val POST_REQUEST_PATH = "/post"

internal class PostService(
    private val client: HttpClient,
) {

    suspend fun addPost(
        byteArrays: List<ByteArray?>,
        message: String?,
        userId: Int
    ): PostResponse = client.submitFormWithBinaryData(
        url = "$BASE_URL${POST_REQUEST_PATH}/add",
        formData = formData {
            append("user_id", userId.toString())
            if (message != null) append("message", message)
            for ((index, byteArray) in byteArrays.withIndex()) {
                if (byteArray != null) append("image$index", byteArray, Headers.build {
                    append(HttpHeaders.ContentType, "image/jpeg")
                    append(HttpHeaders.ContentDisposition, "filename=image$index.png")
                })
            }
        }
    ) {
        onUpload { bytesSentTotal, contentLength ->
            Napier.i("Sent $bytesSentTotal bytes from $contentLength")
        }
    }.body()

    suspend fun fetchUserPosts(
        userId: Int
    ): Result<PostListResponse> = client.request("$POST_REQUEST_PATH/list/${userId}") {
        method = HttpMethod.Get
    }

    suspend fun fetchPostById(
        postId: Int
    ): Result<PostResponse> = client.request("/post/${postId}") {
        method = HttpMethod.Get
    }

    suspend fun fetchRecommendedPosts(
        params: RecommendedPostsParam
    ): Result<PostListResponse> = client.request("$POST_REQUEST_PATH/recommended") {
        method = HttpMethod.Get
        parameter("page", params.page)
        parameter("page_size", params.pageSize)
        parameter("user_id", params.userId)
    }
}
