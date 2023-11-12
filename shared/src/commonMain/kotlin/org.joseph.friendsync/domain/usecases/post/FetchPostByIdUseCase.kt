package org.joseph.friendsync.domain.usecases.post

import org.joseph.friendsync.domain.models.PostDomain
import org.joseph.friendsync.domain.repository.PostRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.joseph.friendsync.common.util.Result

class FetchPostByIdUseCase : KoinComponent {

    private val repository by inject<PostRepository>()


    suspend operator fun invoke(postId: Int): Result<PostDomain> {
        return repository.fetchPostById(postId)
    }
}