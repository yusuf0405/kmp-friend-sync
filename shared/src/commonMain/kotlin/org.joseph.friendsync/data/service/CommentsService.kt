package org.joseph.friendsync.data.service

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import org.joseph.friendsync.common.data.KtorApi
import org.joseph.friendsync.data.models.category.CategoriesResponse
import org.joseph.friendsync.data.models.category.CategoryResponse
import org.joseph.friendsync.data.models.comments.AddCommentParams
import org.joseph.friendsync.data.models.comments.CommentListResponse
import org.joseph.friendsync.data.models.comments.CommentResponse
import org.joseph.friendsync.models.comments.EditCommentParams

private const val COMMENTS_REQUEST_PATH = "/comments"

internal class CommentsService : KtorApi() {

    suspend fun addCommentToPost(
        userId: Int,
        postId: Int,
        commentText: String
    ): CommentResponse = client.get {
        endPoint(path = "$COMMENTS_REQUEST_PATH/add")
        setBody(AddCommentParams(userId, postId, commentText))
    }.body()


    suspend fun deleteCommentById(commentId: Int): Int = client.get {
        endPoint(path = "$COMMENTS_REQUEST_PATH/delete/$commentId")
    }.body()


    suspend fun editCommentById(commentId: Int, editedText: String): Int = client.get {
        endPoint(path = "$COMMENTS_REQUEST_PATH/edit")
        setBody(EditCommentParams(commentId, editedText))
    }.body()


    suspend fun fetchAllPostComments(postId: Int): CommentListResponse = client.get {
        endPoint(path = "$COMMENTS_REQUEST_PATH/$postId")
    }.body()
}