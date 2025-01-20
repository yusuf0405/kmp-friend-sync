package org.joseph.friendsync.data.cloud.service.like

import org.joseph.friendsync.data.models.like.LikedPostListResponse
import org.joseph.friendsync.data.models.like.LikedPostResponse

internal interface PostLikeService {

    suspend fun fetchLikedPosts(userId: Int): LikedPostListResponse

    suspend fun likePost(userId: Int, postId: Int): LikedPostResponse

    suspend fun unlikePost(userId: Int, postId: Int)
}