package org.joseph.friendsync.data.service

import io.ktor.client.call.body
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import org.joseph.friendsync.common.data.BASE_URL
import org.joseph.friendsync.common.data.KtorApi
import org.joseph.friendsync.data.models.post.PostResponse
import org.joseph.friendsync.data.models.post.PostListResponse
import org.joseph.friendsync.data.models.post.RecommendedPostsParam


internal class PostService : KtorApi() {

    suspend fun addPost(
        byteArrays: List<ByteArray?>,
        message: String?,
        userId: Int
    ): PostResponse = client.submitFormWithBinaryData(
        url = "$BASE_URL/post/add",
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
            println("Sent $bytesSentTotal bytes from $contentLength")
        }
    }.body()

    suspend fun fetchUserPosts(userId: Int): PostListResponse = client.get {
        endPoint(path = "/post/list/${userId}")
    }.body()

    suspend fun fetchPostById(postId: Int): PostResponse = client.get {
        endPoint(path = "/post/${postId}")
    }.body()

    suspend fun fetchRecommendedPosts(
        params: RecommendedPostsParam
    ): PostListResponse = client.get {
        endPoint(path = "/post/recommended")
        parameter("page", params.page)
        parameter("page_size", params.pageSize)
        parameter("user_id", params.userId)
    }.body()
}
