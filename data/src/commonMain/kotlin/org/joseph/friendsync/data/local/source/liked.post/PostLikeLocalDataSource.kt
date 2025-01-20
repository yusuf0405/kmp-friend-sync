package org.joseph.friendsync.data.local.source.liked.post

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.data.models.LikedPostData

internal interface PostLikeLocalDataSource {

    fun observeAllLikesPosts(userId: Int): Flow<List<LikedPostData>>

    suspend fun getAllLikesPosts(userId: Int): List<LikedPostData>

    suspend fun getLikesPostById(id: Long): LikedPostData

    suspend fun deleteLikesPostById(id: Long)

    suspend fun addLikedPost(likePost: LikedPostData)

    suspend fun addLikedPosts(likePosts: List<LikedPostData>)
}