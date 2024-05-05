package org.joseph.friendsync.data.service

import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import org.joseph.friendsync.data.models.comments.AddCommentParams
import org.joseph.friendsync.data.models.comments.CommentListResponse
import org.joseph.friendsync.data.models.comments.CommentResponse
import org.joseph.friendsync.data.request
import org.joseph.friendsync.data.utils.ADD_REQUEST_PATH
import org.joseph.friendsync.data.utils.COMMENTS_REQUEST_PATH
import org.joseph.friendsync.data.utils.DELETE_REQUEST_PATH
import org.joseph.friendsync.data.utils.EDIT_REQUEST_PATH
import org.joseph.friendsync.models.comments.EditCommentParams
import org.joseph.friendsync.core.Result

internal class CommentsService(private val client: HttpClient) {

    suspend fun addCommentToPost(
        userId: Int,
        postId: Int,
        commentText: String
    ): Result<CommentResponse> {
        return client.request("$COMMENTS_REQUEST_PATH$ADD_REQUEST_PATH") {
            method = HttpMethod.Post
            setBody(AddCommentParams(userId, postId, commentText))
        }
    }

    suspend fun deleteCommentById(commentId: Int): Result<Int> {
        return client.request("$COMMENTS_REQUEST_PATH$DELETE_REQUEST_PATH/$commentId") {
            method = HttpMethod.Post
        }
    }

    suspend fun editCommentById(commentId: Int, editedText: String): Result<Int> {
        return client.request("$COMMENTS_REQUEST_PATH$EDIT_REQUEST_PATH") {
            method = HttpMethod.Post
            setBody(EditCommentParams(commentId, editedText))
        }
    }

    suspend fun fetchAllPostComments(postId: Int): Result<CommentListResponse> {
        return client.request("$COMMENTS_REQUEST_PATH/$postId") {
            method = HttpMethod.Get
        }
    }
}