package org.joseph.friendsync.domain.usecases.post

import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.domain.repository.PostRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FetchRecommendedPostsUseCase : KoinComponent {

    private val repository by inject<PostRepository>()

    suspend operator fun invoke(page: Int, pageSize: Int, userId: Int): List<PostDomain> {
        return repository.fetchRecommendedPosts(page, pageSize, userId)
    }
}