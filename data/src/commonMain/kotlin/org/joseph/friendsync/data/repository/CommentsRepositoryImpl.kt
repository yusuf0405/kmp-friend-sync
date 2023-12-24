package org.joseph.friendsync.data.repository

import kotlinx.coroutines.withContext
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.DispatcherProvider
import org.joseph.friendsync.common.util.coroutines.callSafe
import org.joseph.friendsync.common.util.map
import org.joseph.friendsync.data.mappers.CommentCloudToCommentDomainMapper
import org.joseph.friendsync.data.models.comments.CommentListResponse
import org.joseph.friendsync.data.models.comments.CommentResponse
import org.joseph.friendsync.data.service.CommentsService
import org.joseph.friendsync.domain.models.CommentDomain
import org.joseph.friendsync.domain.repository.CommentsRepository

internal class CommentsRepositoryImpl(
    private val commentsService: CommentsService,
    private val commentCloudToCommentDomainMapper: CommentCloudToCommentDomainMapper,
    private val dispatcherProvider: DispatcherProvider,
) : CommentsRepository {

    override suspend fun addCommentToPost(
        userId: Int,
        postId: Int,
        commentText: String
    ): Result<CommentDomain?> = withContext(dispatcherProvider.io) {
        callSafe {
            commentsService
                .addCommentToPost(userId, postId, commentText)
                .map(::mapCommentResponseToDomain)
        }
    }

    override suspend fun deleteCommentById(
        commentId: Int
    ): Result<Int> = withContext(dispatcherProvider.io) {
        callSafe { commentsService.deleteCommentById(commentId) }
    }

    override suspend fun editCommentById(
        commentId: Int,
        editedText: String
    ): Result<Int> = withContext(dispatcherProvider.io) {
        callSafe { commentsService.editCommentById(commentId, editedText) }
    }

    override suspend fun fetchAllPostComments(
        postId: Int
    ): Result<List<CommentDomain>> = withContext(dispatcherProvider.io) {
        callSafe {
            commentsService.fetchAllPostComments(postId).map(::mapCommentListResponseToDomain)
        }
    }

    private fun mapCommentListResponseToDomain(response: CommentListResponse): List<CommentDomain> {
        return response.data?.map(commentCloudToCommentDomainMapper::map) ?: emptyList()
    }

    private fun mapCommentResponseToDomain(response: CommentResponse): CommentDomain? {
        return response.data?.let(commentCloudToCommentDomainMapper::map)
    }
}