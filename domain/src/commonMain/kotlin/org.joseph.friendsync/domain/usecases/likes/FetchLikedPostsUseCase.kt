package org.joseph.friendsync.domain.usecases.likes

import org.joseph.friendsync.core.Result
import org.joseph.friendsync.domain.models.LikedPostDomain
import org.joseph.friendsync.domain.repository.PostLikesRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface FetchLikedPostsUseCase {

    suspend operator fun invoke(userId: Int): Result<List<LikedPostDomain>>
}

class FetchLikedPostsUseCaseImpl : FetchLikedPostsUseCase, KoinComponent {

    private val repository by inject<PostLikesRepository>()

    override suspend operator fun invoke(userId: Int): Result<List<LikedPostDomain>> {
        return repository.fetchLikedPosts(userId)
    }
}