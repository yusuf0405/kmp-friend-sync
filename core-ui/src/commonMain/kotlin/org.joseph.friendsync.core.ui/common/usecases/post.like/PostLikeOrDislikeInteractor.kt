package org.joseph.friendsync.core.ui.common.usecases.post.like

import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.domain.repository.PostLikesRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PostLikeOrDislikeInteractor : KoinComponent {

    private val repository by inject<PostLikesRepository>()

    suspend fun likePost(userId: Int, postId: Int): Result<Unit> {
        return repository.likePost(userId, postId)
    }

    suspend fun unlikePost(userId: Int, postId: Int): Result<Unit> {
        return repository.unlikePost(userId, postId)
    }
}