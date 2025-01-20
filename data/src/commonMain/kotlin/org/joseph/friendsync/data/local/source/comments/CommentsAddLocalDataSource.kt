package org.joseph.friendsync.data.local.source.comments

import org.joseph.friendsync.data.models.comments.CommentData

internal interface CommentsAddLocalDataSource {

    /**
     * Добавляет комментарий в локальную базу данных.
     *
     * Выполняет вставку или обновление комментария в контексте диспетчера [dispatcherProvider.io].
     * В случае ошибки выбрасывает [IllegalStateException].
     *
     * @param comment Комментарий [CommentData], который нужно добавить или обновить.
     * @throws IllegalStateException если не удалось добавить комментарий.
     */
    suspend fun addComment(comment: CommentData)

    /**
     * Добавляет список комментариев в локальную базу данных.
     *
     * Выполняет вставку или обновление комментариев в контексте диспетчера [dispatcherProvider.io].
     * В случае ошибки выбрасывает [IllegalStateException].
     *
     * @param comments Список комментариев [CommentData], которые нужно добавить или обновить.
     * @throws IllegalStateException если не удалось добавить комментарии.
     */
    suspend fun addComments(comments: List<CommentData>)
}