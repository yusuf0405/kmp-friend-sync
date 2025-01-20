package org.joseph.friendsync.domain.repository

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.domain.models.LikedPostDomain

interface PostLikesRepository {

    fun observeLikedPosts(userId: Int): Flow<List<LikedPostDomain>>

    suspend fun fetchLikedPosts(userId: Int): List<LikedPostDomain>

    suspend fun likePost(userId: Int, postId: Int)

    suspend fun unlikePost(userId: Int, postId: Int)
}