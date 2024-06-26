package org.joseph.friendsync.domain.usecases.comments

import org.joseph.friendsync.core.Result
import org.joseph.friendsync.domain.models.CommentDomain
import org.joseph.friendsync.domain.repository.CommentsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddCommentToPostUseCase : KoinComponent {

    private val repository by inject<CommentsRepository>()

    suspend operator fun invoke(
        userId: Int,
        postId: Int,
        commentText: String,
    ): Result<CommentDomain?> {
        if (commentText.isBlank()) {
            return Result.Error(message = "You cannot create a comment empty")
        }
        return repository.addCommentToPost(userId, postId, commentText)
    }
}