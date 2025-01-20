package org.joseph.friendsync.domain.usecases.post

import org.joseph.friendsync.core.Result
import org.joseph.friendsync.domain.repository.PostLikesRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PostLikeOrDislikeInteractor : KoinComponent {

    private val repository by inject<PostLikesRepository>()

    suspend fun likePost(userId: Int, postId: Int): Result<Unit> {
        return Result.Success(repository.likePost(userId, postId))
    }

    suspend fun unlikePost(userId: Int, postId: Int): Result<Unit> {
        return Result.Success(repository.unlikePost(userId, postId))
    }
}