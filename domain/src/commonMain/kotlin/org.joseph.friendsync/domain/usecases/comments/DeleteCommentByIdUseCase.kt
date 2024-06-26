package org.joseph.friendsync.domain.usecases.comments

import org.joseph.friendsync.core.Result
import org.joseph.friendsync.domain.repository.CommentsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DeleteCommentByIdUseCase : KoinComponent {

    private val repository by inject<CommentsRepository>()

    suspend operator fun invoke(postId: Int, commentId: Int): Result<Int> {
        return repository.deleteCommentById(postId, commentId)
    }
}