package org.joseph.friendsync.domain.usecases.post

import org.joseph.friendsync.core.Result
import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.domain.repository.PostRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchPostsByQueryUseCase : KoinComponent {

    private val repository by inject<PostRepository>()

    suspend operator fun invoke(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<PostDomain>> {
        return  Result.Success(repository.searchPosts(query, page, pageSize))
    }
}