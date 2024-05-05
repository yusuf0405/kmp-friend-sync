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
import org.joseph.friendsync.core.Result
import org.joseph.friendsync.data.di.BASE_URL
import org.joseph.friendsync.data.models.post.PostListResponse
import org.joseph.friendsync.data.models.post.PostResponse
import org.joseph.friendsync.data.models.post.RecommendedPostsParam
import org.joseph.friendsync.data.request
import org.joseph.friendsync.data.utils.ADD_REQUEST_PATH
import org.joseph.friendsync.data.utils.LIST_REQUEST_PATH
import org.joseph.friendsync.data.utils.MESSAGE_PARAM
import org.joseph.friendsync.data.utils.PAGE_PARAM
import org.joseph.friendsync.data.utils.PAGE_SIZE_PARAM
import org.joseph.friendsync.data.utils.POST_REQUEST_PATH
import org.joseph.friendsync.data.utils.QUERY_PARAM
import org.joseph.friendsync.data.utils.RECOMMENDED_REQUEST_PATH
import org.joseph.friendsync.data.utils.SEARCH_REQUEST_PATCH
import org.joseph.friendsync.data.utils.USER_ID_PARAM


internal class PostService(
    private val client: HttpClient,
) {

    suspend fun addPost(
        byteArrays: List<ByteArray?>,
        message: String?,
        userId: Int
    ): PostResponse = client.submitFormWithBinaryData(
        url = "$BASE_URL${POST_REQUEST_PATH}$ADD_REQUEST_PATH",
        formData = formData {
            append("user_id", userId.toString())
            if (message != null) append(MESSAGE_PARAM, message)
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
    ): Result<PostListResponse> = client.request("$POST_REQUEST_PATH$LIST_REQUEST_PATH/${userId}") {
        method = HttpMethod.Get
    }

    suspend fun fetchPostById(
        postId: Int
    ): Result<PostResponse> = client.request("$POST_REQUEST_PATH/${postId}") {
        method = HttpMethod.Get
    }

    suspend fun fetchRecommendedPosts(
        page: Int,
        pageSize: Int,
        userId: Int
    ): Result<PostListResponse> = client.request("$POST_REQUEST_PATH$RECOMMENDED_REQUEST_PATH") {
        method = HttpMethod.Get
        parameter(PAGE_PARAM, page)
        parameter(PAGE_SIZE_PARAM, pageSize)
        parameter(USER_ID_PARAM, userId)
    }

    suspend fun searchPosts(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<PostListResponse> = client.request("$POST_REQUEST_PATH$SEARCH_REQUEST_PATCH") {
        method = HttpMethod.Get
        parameter(PAGE_PARAM, page)
        parameter(PAGE_SIZE_PARAM, pageSize)
        parameter(QUERY_PARAM, query)
    }
}
