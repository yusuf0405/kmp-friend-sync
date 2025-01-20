package org.joseph.friendsync.data.local.source.comments

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.data.local.dao.comments.CommentsDao
import org.joseph.friendsync.data.local.models.CommentEntity

/**
 * Реализация локального источника данных для удаления комментариев.
 *
 * Класс предоставляет методы для удаления комментариев из локальной базы данных.
 *
 * @property commentsDao DAO для доступа к комментариям в базе данных.
 * @property dispatcherProvider Провайдер диспетчеров для переключения контекста выполнения корутин.
 */
internal class CommentsDeleteLocalDataSourceImpl(
    private val commentsDao: CommentsDao,
    private val dispatcherProvider: DispatcherProvider
) : CommentsDeleteLocalDataSource {

    override suspend fun deleteComment(commentEntity: CommentEntity) {
        withContext(dispatcherProvider.io) {
            try {
                commentsDao.deleteComment(commentEntity)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                throw IllegalStateException("Failed to delete comment in cache", e)
            }
        }
    }

    override suspend fun deleteCommentById(commentId: Int) {
        withContext(dispatcherProvider.io) {
            try {
                commentsDao.deleteCommentById(commentId)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                throw IllegalStateException("Failed to delete comment in cache", e)
            }
        }
    }
}