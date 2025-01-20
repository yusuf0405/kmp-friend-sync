package org.joseph.friendsync.domain.repository

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.domain.models.AddCommentParams
import org.joseph.friendsync.domain.models.CommentDomain

typealias CommentId = Int

interface CommentsRepository {

    /**
     * Наблюдает за всеми комментариями к посту.
     *
     * Возвращает поток [Flow] с списком [CommentDomain], полученных из локального источника данных.
     *
     * @param postId Идентификатор поста, для которого нужно наблюдать за комментариями.
     * @return Поток [Flow] с обновляемым списком [CommentDomain].
     */
    fun observeAllPostComments(postId: Int): Flow<List<CommentDomain>>

    /**
     * Получает комментарии сначала из облачного источника данных, затем добавляет их в локальную базу данных,
     * а затем возвращает список [CommentDomain].
     *
     * @param postId Идентификатор поста, для которого нужно получить комментарии.
     * @return Список [CommentDomain] с комментариями к посту.
     */
    suspend fun fetchAllPostComments(postId: Int): List<CommentDomain>

    /**
     * Добавляет комментарий через облачный источник данных, затем добавляет его в локальную базу данных,
     * а затем возвращает [CommentDomain].
     *
     * @param params Параметры для добавления комментария [AddCommentParams].
     * @return [CommentDomain] добавленного комментария.
     */
    suspend fun addCommentToPost(params: AddCommentParams): CommentDomain

    /**
     * Удаляет комментарий сначала из облачного источника данных, затем из локальной базы данных.
     *
     * @param postId Идентификатор поста, к которому принадлежит комментарий.
     * @param commentId Идентификатор комментария, который нужно удалить.
     * @return Идентификатор удаленного комментария [CommentId].
     */
    suspend fun deleteCommentById(postId: Int, commentId: Int): CommentId

    /**
     * Редактирует комментарий через облачный источник данных, затем редактирует его в локальной базе данных.
     *
     * @param commentId Идентификатор комментария, который нужно отредактировать.
     * @param editedText Новый текст комментария.
     * @return Идентификатор отредактированного комментария [CommentId].
     */
    suspend fun editCommentById(commentId: Int, editedText: String): CommentId
}