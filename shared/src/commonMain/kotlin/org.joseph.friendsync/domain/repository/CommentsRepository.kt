package org.joseph.friendsync.domain.repository

import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.domain.models.CommentDomain

interface CommentsRepository {

    suspend fun addCommentToPost(
        userId: Int,
        postId: Int,
        commentText: String
    ): Result<CommentDomain?>


    suspend fun deleteCommentById(commentId: Int): Result<Int>


    suspend fun editCommentById(commentId: Int, editedText: String): Result<Int>


    suspend fun fetchAllPostComments(postId: Int): Result<List<CommentDomain>>
}