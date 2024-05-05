package org.joseph.friendsync.domain.repository

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.core.Result
import org.joseph.friendsync.domain.models.LikedPostDomain

interface PostLikesRepository {

    suspend fun fetchLikedPosts(userId: Int): Result<List<LikedPostDomain>>

    fun observeLikedPosts(userId: Int): Flow<List<LikedPostDomain>>

    suspend fun likePost(userId: Int, postId: Int): Result<Unit>

    suspend fun unlikePost(userId: Int, postId: Int): Result<Unit>
}