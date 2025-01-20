package org.joseph.friendsync.data.cloud.service.comments

import org.joseph.friendsync.data.models.comments.AddCommentRequestParams
import org.joseph.friendsync.data.cloud.models.CommentListResponse
import org.joseph.friendsync.data.cloud.models.CommentResponse
import org.joseph.friendsync.domain.repository.CommentId

internal interface CommentsService {

    /**
     * Получает список комментариев для заданного поста.
     *
     * Выполняет GET-запрос для получения комментариев к посту с заданным идентификатором.
     *
     * @param postId Идентификатор поста, для которого нужно получить комментарии.
     * @return [CommentListResponse] с полученными комментариями.
     */
    suspend fun fetchAllPostComments(postId: Int): CommentListResponse

    /**
     * Добавляет комментарий к посту.
     *
     * Выполняет POST-запрос для добавления комментария к посту с заданными параметрами.
     *
     * @param params Параметры для добавления комментария [AddCommentRequestParams].
     * @return [CommentResponse] с данными добавленного комментария.
     */
    suspend fun addCommentToPost(params: AddCommentRequestParams): CommentResponse

    /**
     * Удаляет комментарий по его идентификатору.
     *
     * Выполняет POST-запрос для удаления комментария с заданным идентификатором.
     *
     * @param commentId Идентификатор комментария, который нужно удалить.
     * @return Идентификатор удаленного комментария [CommentId].
     */
    suspend fun deleteCommentById(commentId: Int): CommentId

    /**
     * Редактирует комментарий по его идентификатору.
     *
     * Выполняет POST-запрос для редактирования комментария с заданным идентификатором и новым текстом.
     *
     * @param commentId Идентификатор комментария, который нужно отредактировать.
     * @param editedText Новый текст комментария.
     * @return Идентификатор отредактированного комментария [CommentId].
     */
    suspend fun editCommentById(commentId: Int, editedText: String): CommentId
}