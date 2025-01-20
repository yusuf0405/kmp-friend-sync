package org.joseph.friendsync.domain.usecases.comments

import org.joseph.friendsync.domain.repository.CommentId
import org.joseph.friendsync.domain.repository.CommentsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Use Case для редактирования комментария.
 *
 * Класс предоставляет оператор для редактирования комментария по его идентификатору.
 */
class E
class EditCommentUseCase : KoinComponent {

    private val repository by inject<CommentsRepository>()

    /**
     * Редактирует комментарий по его идентификатору.
     *
     * @param commentId Идентификатор комментария, который нужно отредактировать.
     * @param commentText Новый текст комментария.
     * @return Идентификатор отредактированного комментария [CommentId].
     */
    suspend operator fun invoke(commentId: Int, commentText: String): CommentId {
        return repository.editCommentById(commentId, commentText)
    }
}