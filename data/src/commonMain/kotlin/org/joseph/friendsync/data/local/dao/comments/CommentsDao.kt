package org.joseph.friendsync.data.local.dao.comments

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.data.local.models.CommentLocal
import org.joseph.friendsync.data.models.comments.CommentCloud

interface CommentsDao {

    fun fetchAllPostCommentsReactive(postId: Int): Flow<List<CommentLocal>>

    suspend fun fetchAllPostComments(postId: Int): List<CommentLocal>

    suspend fun insertOrUpdateComment(commentCloud: CommentCloud)

    suspend fun insertOrUpdateComments(commentsCloud: List<CommentCloud>)

    suspend fun deleteCommentById(commentId: Int)

    suspend fun editCommentById(commentId: Int, editedText: String)
}