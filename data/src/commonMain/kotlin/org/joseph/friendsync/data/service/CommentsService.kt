package org.joseph.friendsync.data.service

import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.data.models.comments.AddCommentParams
import org.joseph.friendsync.data.models.comments.CommentListResponse
import org.joseph.friendsync.data.models.comments.CommentResponse
import org.joseph.friendsync.data.request
import org.joseph.friendsync.models.comments.EditCommentParams

private const val COMMENTS_REQUEST_PATH = "/comments"

internal class CommentsService(
    private val client: HttpClient
) {

    suspend fun addCommentToPost(
        userId: Int,
        postId: Int,
        commentText: String
    ): Result<CommentResponse> = client.request("$COMMENTS_REQUEST_PATH/add") {
        method = HttpMethod.Post
        setBody(AddCommentParams(userId, postId, commentText))
    }

    suspend fun deleteCommentById(
        commentId: Int
    ): Result<Int> = client.request("$COMMENTS_REQUEST_PATH/delete/$commentId") {
        method = HttpMethod.Post
    }

    suspend fun editCommentById(
        commentId: Int,
        editedText: String
    ): Result<Int> = client.request("$COMMENTS_REQUEST_PATH/edit") {
        method = HttpMethod.Post
        setBody(EditCommentParams(commentId, editedText))
    }

    suspend fun fetchAllPostComments(
        postId: Int
    ): Result<CommentListResponse> = client.request("$COMMENTS_REQUEST_PATH/$postId") {
        method = HttpMethod.Get
    }
}