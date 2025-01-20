package org.joseph.friendsync.domain.usecases.comments

import org.joseph.friendsync.domain.models.AddCommentParams
import org.joseph.friendsync.domain.models.CommentDomain
import org.joseph.friendsync.domain.repository.CommentsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddCommentToPostUseCase : KoinComponent {

    private val repository by inject<CommentsRepository>()

    /**
     * Добавляет комментарий к посту.
     *
     * Функция принимает параметры для добавления комментария и возвращает результат операции.
     * Если текст комментария пуст, возвращается ошибка [IllegalStateException].
     *
     * @param params параметры для добавления комментария, содержащие текст комментария и другую необходимую информацию.
     * @return Объект [CommentDomain] при успешном добавлении комментария или с исключением, если текст комментария пуст.
     *
     * @throws IllegalStateException если текст комментария пуст.
     */
    suspend operator fun invoke(params: AddCommentParams): CommentDomain {
        if (params.commentText.isBlank()) {
            throw IllegalStateException("You cannot create a comment empty")
        }
        return repository.addCommentToPost(params)
    }
}