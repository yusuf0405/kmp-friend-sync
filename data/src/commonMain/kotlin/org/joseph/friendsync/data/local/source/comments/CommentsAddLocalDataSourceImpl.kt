package org.joseph.friendsync.data.local.source.comments

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.data.local.dao.comments.CommentsDao
import org.joseph.friendsync.data.local.models.CommentEntity
import org.joseph.friendsync.data.mappers.CommentDataToLocalMapper
import org.joseph.friendsync.data.models.comments.CommentData

/**
 * Реализация локального источника данных для добавления комментариев.
 *
 * Класс предоставляет методы для добавления одного или нескольких комментариев в локальную базу данных.
 *
 * @property commentsDao DAO для доступа к комментариям в базе данных.
 * @property dispatcherProvider Провайдер диспетчеров для переключения контекста выполнения корутин.
 * @property commentDataToLocalMapper Маппер из объекта [CommentData] в [CommentEntity].
 */
internal class CommentsAddLocalDataSourceImpl(
    private val commentsDao: CommentsDao,
    private val dispatcherProvider: DispatcherProvider,
    private val commentDataToLocalMapper: CommentDataToLocalMapper
) : CommentsAddLocalDataSource {

    override suspend fun addComment(comment: CommentData) {
        withContext(dispatcherProvider.io) {
            try {
                commentsDao.insertOrUpdateComment(commentDataToLocalMapper.map(comment))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                throw IllegalStateException("Failed to add comment in cache", e)
            }
        }
    }

    override suspend fun addComments(comments: List<CommentData>) {
        withContext(dispatcherProvider.io) {
            try {
                val commentsLocal = comments.map(commentDataToLocalMapper::map)
                commentsDao.insertOrUpdateComments(commentsLocal)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                throw IllegalStateException("Failed to add comments in cache", e)
            }
        }
    }
}