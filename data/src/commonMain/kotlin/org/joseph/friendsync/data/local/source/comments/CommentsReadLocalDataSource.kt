package org.joseph.friendsync.data.local.source.comments

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.data.local.models.CommentEntity
import org.joseph.friendsync.data.models.comments.CommentData

internal interface CommentsReadLocalDataSource {

    /**
     * Получает список комментариев для заданного поста из локальной базы данных.
     *
     * Выполняет запрос на получение комментариев в контексте диспетчера [dispatcherProvider.io].
     * В случае ошибки выбрасывает [IllegalStateException].
     *
     * @param postId Идентификатор поста, для которого нужно получить комментарии.
     * @return Список комментариев [CommentData].
     * @throws IllegalStateException если не удалось получить комментарии.
     */
    suspend fun fetchComments(postId: Int): List<CommentData>

    /**
     * Наблюдает за изменениями комментариев для заданного поста в локальной базе данных.
     *
     * Выполняет наблюдение в контексте диспетчера [dispatcherProvider.io].
     * В случае ошибки выбрасывает [IllegalStateException].
     *
     * @param postId Идентификатор поста, для которого нужно наблюдать за комментариями.
     * @return Поток [Flow] с обновляемым списком комментариев [CommentData].
     * @throws IllegalStateException если не удалось наблюдать за комментариями.
     */
    fun observeComments(postId: Int): Flow<List<CommentData>>
}