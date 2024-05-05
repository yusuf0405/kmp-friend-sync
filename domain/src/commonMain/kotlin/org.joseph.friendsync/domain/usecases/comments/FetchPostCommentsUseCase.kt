package org.joseph.friendsync.domain.usecases.comments

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.core.Result
import org.joseph.friendsync.domain.models.CommentDomain
import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.domain.repository.CommentsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FetchPostCommentsUseCase : KoinComponent {

    private val repository by inject<CommentsRepository>()

    suspend operator fun invoke(postId: Int): Result<List<CommentDomain>> {
        return repository.fetchAllPostComments(postId)
    }

    fun observeAllPostComments(postId: Int): Flow<List<CommentDomain>> {
        return repository.observeAllPostComments(postId)
    }
}