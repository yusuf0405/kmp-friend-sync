package org.joseph.friendsync.data.local.dao.comments

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joseph.friendsync.common.util.coroutines.DispatcherProvider
import org.joseph.friendsync.data.local.mappers.CommentsSqlToCommentLocalMapper
import org.joseph.friendsync.data.local.models.CommentLocal
import org.joseph.friendsync.data.models.comments.CommentCloud
import org.joseph.friendsync.database.AppDatabase

class CommentsDaoImpl(
    private val appDatabase: AppDatabase,
    private val dispatcherProvider: DispatcherProvider,
    private val commentsSqlToCommentLocalMapper: CommentsSqlToCommentLocalMapper
) : CommentsDao {

    override fun fetchAllPostCommentsReactive(postId: Int): Flow<List<CommentLocal>> {
        return commentsQueries
            .selectAllCommentsForPost(postId.toLong())
            .asFlow()
            .mapToList(dispatcherProvider.io)
            .map { comments ->
                comments.map(commentsSqlToCommentLocalMapper::map)
            }
    }

    override suspend fun fetchAllPostComments(postId: Int): List<CommentLocal> {
        return commentsQueries
            .selectAllCommentsForPost(postId.toLong())
            .executeAsList()
            .map(commentsSqlToCommentLocalMapper::map)
    }

    private val commentsQueries by lazy { appDatabase.commentsQueries }

    override suspend fun insertOrUpdateComment(
        commentCloud: CommentCloud
    ) = withContext(dispatcherProvider.io) {
        commentsQueries.insertComment(
            id = commentCloud.id.toLong(),
            comment = commentCloud.comment,
            release_date = commentCloud.releaseDate,
            likes_count = commentCloud.likesCount.toLong(),
            post_id = commentCloud.postId.toLong(),
            user_id = commentCloud.user.id.toLong(),
            user_avatar = commentCloud.user.avatar,
            user_release_date = commentCloud.user.releaseDate,
            user_name = commentCloud.user.name,
            user_lastname = commentCloud.user.lastName
        )
    }

    override suspend fun insertOrUpdateComments(commentsCloud: List<CommentCloud>) {
        commentsCloud.forEach {
            insertOrUpdateComment(it)
        }
    }

    override suspend fun deleteCommentById(commentId: Int) = withContext(dispatcherProvider.io) {
        commentsQueries.deleteCommentById(commentId.toLong())
    }

    override suspend fun editCommentById(commentId: Int, editedText: String) {
        commentsQueries.updateCommentTextById(editedText, commentId.toLong())
    }
}