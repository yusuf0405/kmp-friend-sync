package org.joseph.friendsync.data.local.source.comments

import org.joseph.friendsync.data.local.models.CommentEntity

internal interface CommentsDeleteLocalDataSource {

    /**
     * Удаляет комментарий из локальной базы данных.
     *
     * Выполняет удаление комментария в контексте диспетчера [dispatcherProvider.io].
     * В случае ошибки выбрасывает [IllegalStateException].
     *
     * @param commentEntity Комментарий [CommentEntity], который нужно удалить.
     * @throws IllegalStateException если не удалось удалить комментарий.
     */
    suspend fun deleteComment(commentEntity: CommentEntity)

    /**
     * Удаляет комментарий по его идентификатору из локальной базы данных.
     *
     * Выполняет удаление комментария по его идентификатору в контексте диспетчера [dispatcherProvider.io].
     * В случае ошибки выбрасывает [IllegalStateException].
     *
     * @param commentId Идентификатор комментария, который нужно удалить.
     * @throws IllegalStateException если не удалось удалить комментарий.
     */
    suspend fun deleteCommentById(commentId: Int)
}