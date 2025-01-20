package org.joseph.friendsync.data.cloud.service.comments

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.joseph.friendsync.data.models.comments.AddCommentRequestParams
import org.joseph.friendsync.data.cloud.models.CommentListResponse
import org.joseph.friendsync.data.cloud.models.CommentResponse
import org.joseph.friendsync.data.cloud.ADD_REQUEST_PATH
import org.joseph.friendsync.data.cloud.COMMENTS_REQUEST_PATH
import org.joseph.friendsync.data.cloud.DELETE_REQUEST_PATH
import org.joseph.friendsync.data.cloud.EDIT_REQUEST_PATH
import org.joseph.friendsync.domain.repository.CommentId
import org.joseph.friendsync.models.comments.EditCommentParams

/**
 * Реализация сервиса для управления комментариями через HTTP-клиент.
 *
 * Класс предоставляет методы для взаимодействия с API комментариев.
 *
 * @property client HTTP-клиент для отправки запросов к API.
 */

internal class CommentsServiceImpl(private val client: HttpClient) : CommentsService {

    override suspend fun fetchAllPostComments(postId: Int): CommentListResponse {
        return client.get("$COMMENTS_REQUEST_PATH/$postId").body()
    }

    override suspend fun addCommentToPost(params: AddCommentRequestParams): CommentResponse {
        return client.post("$COMMENTS_REQUEST_PATH$ADD_REQUEST_PATH") {
            setBody(params)
        }.body()
    }

    override suspend fun deleteCommentById(commentId: Int): CommentId {
        return client.post("$COMMENTS_REQUEST_PATH$DELETE_REQUEST_PATH/$commentId").body()
    }

    override suspend fun editCommentById(commentId: Int, editedText: String): CommentId {
        return client.post("$COMMENTS_REQUEST_PATH$EDIT_REQUEST_PATH") {
            setBody(EditCommentParams(commentId, editedText))
        }.body()
    }
}