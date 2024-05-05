package org.joseph.friendsync.data.service

import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import org.joseph.friendsync.core.Result
import org.joseph.friendsync.data.models.comments.AddCommentParams
import org.joseph.friendsync.data.models.comments.CommentListResponse
import org.joseph.friendsync.data.models.comments.CommentResponse
import org.joseph.friendsync.data.models.like.LikeOrUnlikeParams
import org.joseph.friendsync.data.models.like.LikedPostListResponse
import org.joseph.friendsync.data.models.like.LikedPostResponse
import org.joseph.friendsync.data.request
import org.joseph.friendsync.data.utils.ADD_REQUEST_PATH
import org.joseph.friendsync.data.utils.COMMENTS_REQUEST_PATH
import org.joseph.friendsync.data.utils.DELETE_REQUEST_PATH
import org.joseph.friendsync.data.utils.EDIT_REQUEST_PATH
import org.joseph.friendsync.data.utils.LIKES_REQUEST_PATH
import org.joseph.friendsync.data.utils.LIKE_REQUEST_PATCH
import org.joseph.friendsync.data.utils.UNLIKE_REQUEST_PATCH
import org.joseph.friendsync.models.comments.EditCommentParams

internal class LikePostService(
    private val client: HttpClient
) {

    suspend fun fetchLikedPosts(
        userId: Int
    ): Result<LikedPostListResponse> = client.request("$LIKES_REQUEST_PATH/${userId}") {
        method = HttpMethod.Get
    }

    suspend fun likePost(
        userId: Int,
        postId: Int,
    ): Result<LikedPostResponse> = client.request("$LIKES_REQUEST_PATH$LIKE_REQUEST_PATCH") {
        method = HttpMethod.Post
        setBody(LikeOrUnlikeParams(userId, postId))
    }

    suspend fun unlikePost(
        userId: Int,
        postId: Int,
    ): Result<Unit> = client.request("$LIKES_REQUEST_PATH$UNLIKE_REQUEST_PATCH") {
        method = HttpMethod.Post
        setBody(LikeOrUnlikeParams(userId, postId))
    }
}