package org.joseph.friendsync.data.cloud.service.like

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.joseph.friendsync.data.cloud.LIKES_REQUEST_PATH
import org.joseph.friendsync.data.cloud.LIKE_REQUEST_PATCH
import org.joseph.friendsync.data.cloud.UNLIKE_REQUEST_PATCH
import org.joseph.friendsync.data.models.like.LikeOrUnlikeParams
import org.joseph.friendsync.data.models.like.LikedPostListResponse
import org.joseph.friendsync.data.models.like.LikedPostResponse

internal class PostLikeServiceImpl(private val client: HttpClient) : PostLikeService {

    override suspend fun fetchLikedPosts(userId: Int): LikedPostListResponse {
        return client.get("$LIKES_REQUEST_PATH/${userId}").body()
    }

    override suspend fun likePost(userId: Int, postId: Int): LikedPostResponse {
        return client.post("$LIKES_REQUEST_PATH$LIKE_REQUEST_PATCH") {
            setBody(LikeOrUnlikeParams(userId, postId))
        }.body()
    }

    override suspend fun unlikePost(userId: Int, postId: Int) {
        return client.post("$LIKES_REQUEST_PATH$UNLIKE_REQUEST_PATCH") {
            setBody(LikeOrUnlikeParams(userId, postId))
        }.body()
    }
}