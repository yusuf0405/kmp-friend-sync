package org.joseph.friendsync.data.cloud.source.comments

import org.joseph.friendsync.data.models.comments.AddCommentRequestParams
import org.joseph.friendsync.data.models.comments.CommentData
import org.joseph.friendsync.domain.repository.CommentId

internal interface CommentsCloudDataSource {

    /**
     * Добавляет комментарий к посту через облачный сервис.
     *
     * Выполняет запрос на добавление комментария в контексте диспетчера [dispatcherProvider.io].
     * В случае ошибки выбрасывает [IllegalStateException].
     *
     * @param params Параметры для добавления комментария [AddCommentRequestParams].
     * @return Объект [CommentData] при успешном добавлении или null, если операция не удалась.
     * @throws IllegalStateException если не удалось добавить комментарий.
     */
    suspend fun addCommentToPost(params: AddCommentRequestParams): CommentData

    /**
     * Получает список комментариев для заданного поста через облачный сервис.
     *
     * Выполняет запрос на получение комментариев в контексте диспетчера [dispatcherProvider.io].
     * В случае ошибки выбрасывает [IllegalStateException].
     *
     * @param postId Идентификатор поста, для которого нужно получить комментарии.
     * @return Список комментариев [CommentData].
     * @throws IllegalStateException если не удалось получить комментарии.
     */
    suspend fun fetchPostComments(postId: Int): List<CommentData>

    /**
     * Удаляет комментарий по его идентификатору через облачный сервис.
     *
     * Выполняет запрос на удаление комментария в контексте диспетчера [dispatcherProvider.io].
     * В случае ошибки выбрасывает [IllegalStateException].
     *
     * @param commentId Идентификатор комментария, который нужно удалить.
     * @return Идентификатор удаленного комментария [CommentId].
     * @throws IllegalStateException если не удалось удалить комментарий.
     */
    suspend fun deleteCommentById(commentId: Int): CommentId

    /**
     * Редактирует комментарий по его идентификатору через облачный сервис.
     *
     * Выполняет запрос на редактирование комментария в контексте диспетчера [dispatcherProvider.io].
     * В случае ошибки выбрасывает [IllegalStateException].
     *
     * @param commentId Идентификатор комментария, который нужно отредактировать.
     * @param editedText Новый текст комментария.
     * @return Идентификатор отредактированного комментария [CommentId].
     * @throws IllegalStateException если не удалось отредактировать комментарий.
     */
    suspend fun editCommentById(commentId: Int, editedText: String): CommentId
}