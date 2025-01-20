package org.joseph.friendsync.data.repository

import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joseph.friendsync.data.cloud.source.comments.CommentsCloudDataSource
import org.joseph.friendsync.data.local.source.comments.CommentsAddLocalDataSource
import org.joseph.friendsync.data.local.source.comments.CommentsDeleteLocalDataSource
import org.joseph.friendsync.data.local.source.comments.CommentsEditLocalDataSource
import org.joseph.friendsync.data.local.source.comments.CommentsReadLocalDataSource
import org.joseph.friendsync.data.local.source.posts.PostAddLocalDataSource
import org.joseph.friendsync.data.mappers.CommentDataToDomainMapper
import org.joseph.friendsync.data.models.comments.CommentData
import org.joseph.friendsync.data.models.comments.toData
import org.joseph.friendsync.domain.models.AddCommentParams
import org.joseph.friendsync.domain.models.CommentDomain
import org.joseph.friendsync.domain.repository.CommentId
import org.joseph.friendsync.domain.repository.CommentsRepository

/**
 * Реализация репозитория для управления комментариями.
 *
 * Класс предоставляет методы для получения, добавления, удаления и редактирования комментариев.
 *
 * @property commentCloudDataSource Облачный источник данных для управления комментариями.
 * @property commentsReadLocalDataSource Локальный источник данных для чтения комментариев.
 * @property commentsAddLocalDataSource Локальный источник данных для добавления комментариев.
 * @property commentsDeleteLocalDataSource Локальный источник данных для удаления комментариев.
 * @property commentsEditLocalDataSource Локальный источник данных для редактирования комментариев.
 * @property postAddLocalDataSource Локальный источник данных для редактирования постов.
 * @property commentDataToDomainMapper Маппер из объекта [CommentData] в [CommentDomain].
 */
internal class CommentsRepositoryImpl(
    private val commentCloudDataSource: CommentsCloudDataSource,
    private val commentsReadLocalDataSource: CommentsReadLocalDataSource,
    private val commentsAddLocalDataSource: CommentsAddLocalDataSource,
    private val commentsDeleteLocalDataSource: CommentsDeleteLocalDataSource,
    private val commentsEditLocalDataSource: CommentsEditLocalDataSource,
    private val postAddLocalDataSource: PostAddLocalDataSource,
    private val commentDataToDomainMapper: CommentDataToDomainMapper,
) : CommentsRepository {

    override fun observeAllPostComments(postId: Int): Flow<List<CommentDomain>> {
        return commentsReadLocalDataSource
            .observeComments(postId)
            .map { comments: List<CommentData> -> comments.map(commentDataToDomainMapper::map) }
    }

    override suspend fun fetchAllPostComments(postId: Int): List<CommentDomain> {
        val commentsData = commentCloudDataSource.fetchPostComments(postId)
        val commentsDomain = commentsData.map(commentDataToDomainMapper::map)
        withContext(NonCancellable) {
            commentsAddLocalDataSource.addComments(commentsData)
        }
        return commentsDomain
    }

    override suspend fun addCommentToPost(params: AddCommentParams): CommentDomain {
        val commentData = commentCloudDataSource.addCommentToPost(params.toData())
        withContext(NonCancellable) {
            commentsAddLocalDataSource.addComment(commentData)
            postAddLocalDataSource.incrementOrDecrementCommentsCount(commentData.postId, true)
        }
        return commentDataToDomainMapper.map(commentData)
    }

    override suspend fun deleteCommentById(postId: Int, commentId: Int): CommentId {
        commentCloudDataSource.deleteCommentById(commentId)
        withContext(NonCancellable) {
            commentsDeleteLocalDataSource.deleteCommentById(commentId)
            postAddLocalDataSource.incrementOrDecrementCommentsCount(postId, false)
        }
        return commentId
    }

    override suspend fun editCommentById(commentId: Int, editedText: String): CommentId {
        commentCloudDataSource.editCommentById(commentId, editedText)
        withContext(NonCancellable) {
            commentsEditLocalDataSource.editCommentById(commentId, editedText)
        }
        return commentId
    }
}