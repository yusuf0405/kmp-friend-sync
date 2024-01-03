package org.joseph.friendsync.data.local.dao.liked.post

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.data.local.models.LikePostLocal
import org.joseph.friendsync.data.models.like.LikedPostCloud

interface LikePostDao {
    suspend fun getAllLikesPosts(userId: Int): List<LikePostLocal>
    fun getAllLikesPostsReactive(userId: Int): Flow<List<LikePostLocal>>
    suspend fun getLikesPostById(id: Long): LikePostLocal?
    suspend fun deleteLikesPostById(id: Long)
    suspend fun insertOrUpdateLikesPost(likePost: LikedPostCloud)
}