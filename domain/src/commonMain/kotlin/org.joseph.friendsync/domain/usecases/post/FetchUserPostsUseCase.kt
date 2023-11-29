package org.joseph.friendsync.domain.usecases.post

import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.domain.repository.PostRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FetchUserPostsUseCase : KoinComponent {

    private val repository by inject<PostRepository>()

    suspend operator fun invoke(
        userId: Int
    ): Result<List<PostDomain>> {
        return repository.fetchUserPosts(userId)
    }
}