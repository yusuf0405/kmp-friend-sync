package org.joseph.friendsync.domain.repository

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.core.Result
import org.joseph.friendsync.domain.models.CommentDomain

interface CommentsRepository {

    suspend fun addCommentToPost(
        userId: Int,
        postId: Int,
        commentText: String
    ): Result<CommentDomain?>

    suspend fun deleteCommentById(postId: Int, commentId: Int): Result<Int>

    suspend fun editCommentById(commentId: Int, editedText: String): Result<Int>

    suspend fun fetchAllPostComments(postId: Int): Result<List<CommentDomain>>

    fun observeAllPostComments(postId: Int): Flow<List<CommentDomain>>
}