package org.joseph.friendsync.data.repository

import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.callSafe
import org.joseph.friendsync.data.mappers.CommentCloudToCommentDomainMapper
import org.joseph.friendsync.data.service.CommentsService
import org.joseph.friendsync.domain.models.CommentDomain
import org.joseph.friendsync.domain.repository.CommentsRepository

internal class CommentsRepositoryImpl(
    private val commentsService: CommentsService,
    private val commentCloudToCommentDomainMapper: CommentCloudToCommentDomainMapper
) : CommentsRepository {
    override suspend fun addCommentToPost(
        userId: Int,
        postId: Int,
        commentText: String
    ): Result<CommentDomain?> = callSafe(
        Result.Error(message = defaultErrorMessage)
    ) {
        val result = commentsService.addCommentToPost(userId, postId, commentText).data
        Result.Success(
            data = if (result != null) commentCloudToCommentDomainMapper.map(result)
            else null
        )
    }

    override suspend fun deleteCommentById(
        commentId: Int
    ): Result<Int> = callSafe(Result.Error(message = defaultErrorMessage)) {
        Result.Success(data = commentsService.deleteCommentById(commentId))
    }

    override suspend fun editCommentById(
        commentId: Int,
        editedText: String
    ): Result<Int> = callSafe(Result.Error(message = defaultErrorMessage)) {
        Result.Success(data = commentsService.editCommentById(commentId, editedText))
    }

    override suspend fun fetchAllPostComments(
        postId: Int
    ): Result<List<CommentDomain>> = callSafe(Result.Error(message = defaultErrorMessage)) {
        val result = commentsService.fetchAllPostComments(postId).data
        Result.Success(
            data = result?.map(commentCloudToCommentDomainMapper::map) ?: emptyList()
        )
    }
}