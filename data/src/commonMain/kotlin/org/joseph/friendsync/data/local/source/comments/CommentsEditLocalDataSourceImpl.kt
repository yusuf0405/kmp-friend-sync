package org.joseph.friendsync.data.local.source.comments

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.data.local.dao.comments.CommentsDao

/**
 * Реализация локального источника данных для редактирования комментариев.
 *
 * Класс предоставляет метод для редактирования комментария в локальной базе данных.
 *
 * @property commentsDao DAO для доступа к комментариям в базе данных.
 * @property dispatcherProvider Провайдер диспетчеров для переключения контекста выполнения корутин.
 */
internal class CommentsEditLocalDataSourceImpl(
    private val commentsDao: CommentsDao,
    private val dispatcherProvider: DispatcherProvider
) : CommentsEditLocalDataSource {

    override suspend fun editCommentById(commentId: Int, editedText: String) {
        withContext(dispatcherProvider.io) {
            try {
                commentsDao.editCommentById(commentId, editedText)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                throw IllegalStateException("Failed to edit comment in cache", e)
            }
        }
    }
}