package org.joseph.friendsync.domain.usecases.comments

import org.joseph.friendsync.domain.repository.CommentId
import org.joseph.friendsync.domain.repository.CommentsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Use Case для удаления комментария по его идентификатору.
 *
 * Класс предоставляет метод для удаления комментария с заданным идентификатором.
 */
class DeleteCommentByIdUseCase : KoinComponent {

    private val repository by inject<CommentsRepository>()

    /**
     * Удаляет комментарий по его идентификатору.
     *
     * @param postId Идентификатор поста, к которому принадлежит комментарий.
     * @param commentId Идентификатор комментария, который нужно удалить.
     * @return Идентификатор удаленного комментария [CommentId].
     */
    suspend operator fun invoke(postId: Int, commentId: Int): CommentId {
        return repository.deleteCommentById(postId, commentId)
    }
}