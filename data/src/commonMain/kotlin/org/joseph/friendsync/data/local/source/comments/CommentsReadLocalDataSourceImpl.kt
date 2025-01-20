package org.joseph.friendsync.data.local.source.comments

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.data.local.dao.comments.CommentsDao
import org.joseph.friendsync.data.local.mappers.CommentsLocalToDataMapper
import org.joseph.friendsync.data.local.models.CommentEntity
import org.joseph.friendsync.data.models.comments.CommentData

/**
 * Реализация локального источника данных для чтения комментариев.
 *
 * Класс предоставляет методы для получения и наблюдения за комментариями из локальной базы данных.
 *
 * @property commentsDao DAO для доступа к комментариям в базе данных.
 * @property dispatcherProvider Провайдер диспетчеров для переключения контекста выполнения корутин.
 * @property commentsLocalToDataMapper Маппер из объекта [CommentEntity] в [CommentData].
 */
internal class CommentsReadLocalDataSourceImpl(
    private val commentsDao: CommentsDao,
    private val dispatcherProvider: DispatcherProvider,
    private val commentsLocalToDataMapper: CommentsLocalToDataMapper,
) : CommentsReadLocalDataSource {

    override suspend fun fetchComments(postId: Int): List<CommentData> {
        return withContext(dispatcherProvider.io) {
            try {
                val comments = commentsDao.fetchPostComments(postId)
                comments.map(commentsLocalToDataMapper::map)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                throw IllegalStateException("Failed to fetch comments in cache", e)
            }
        }
    }

    override fun observeComments(postId: Int): Flow<List<CommentData>> = commentsDao
        .observePostComments(postId)
        .map { comments: List<CommentEntity> -> comments.map(commentsLocalToDataMapper::map) }
        .flowOn(dispatcherProvider.io)
        .catch {
            throw IllegalStateException("Failed to observe comments in cache", it)
        }
}