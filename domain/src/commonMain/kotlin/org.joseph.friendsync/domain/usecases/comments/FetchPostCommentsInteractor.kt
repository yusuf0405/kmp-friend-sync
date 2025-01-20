package org.joseph.friendsync.domain.usecases.comments

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.domain.models.CommentDomain
import org.joseph.friendsync.domain.repository.CommentsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Интерактор для получения комментариев к посту.
 *
 * Класс предоставляет методы для получения списка комментариев к посту и наблюдения за ними.
 */
class FetchPostCommentsInteractor : KoinComponent {

    private val repository by inject<CommentsRepository>()

    /**
     * Получает список комментариев к посту с заданным идентификатором.
     *
     * @param postId Идентификатор поста, для которого нужно получить комментарии.
     * @return Список [CommentDomain] с комментариями к посту.
     */
    suspend fun fetchPostComments(postId: Int): List<CommentDomain> {
        return repository.fetchAllPostComments(postId)
    }

    /**
     * Наблюдает за изменениями списка комментариев к посту с заданным идентификатором.
     *
     * @param postId Идентификатор поста, для которого нужно наблюдать за комментариями.
     * @return Поток [Flow] с обновляемым списком [CommentDomain].
     */
    fun observeAllPostComments(postId: Int): Flow<List<CommentDomain>> {
        return repository.observeAllPostComments(postId)
    }
}