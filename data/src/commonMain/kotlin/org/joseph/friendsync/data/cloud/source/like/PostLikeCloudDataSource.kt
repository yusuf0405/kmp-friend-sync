package org.joseph.friendsync.data.cloud.source.like

import org.joseph.friendsync.data.models.LikedPostData

internal interface PostLikeCloudDataSource {

    suspend fun fetchLikedPosts(userId: Int): List<LikedPostData>

    suspend fun likePost(userId: Int, postId: Int): LikedPostData

    suspend fun unlikePost(userId: Int, postId: Int)

}