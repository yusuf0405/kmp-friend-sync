package org.joseph.friendsync.data.local.source.comments

internal interface CommentsEditLocalDataSource {

    /**
     * Редактирует комментарий по его идентификатору в локальной базе данных.
     *
     * Выполняет редактирование комментария в контексте диспетчера [dispatcherProvider.io].
     * В случае ошибки выбрасывает [IllegalStateException].
     *
     * @param commentId Идентификатор комментария, который нужно отредактировать.
     * @param editedText Новый текст комментария.
     * @throws IllegalStateException если не удалось отредактировать комментарий.
     */
    suspend fun editCommentById(commentId: Int, editedText: String)
}