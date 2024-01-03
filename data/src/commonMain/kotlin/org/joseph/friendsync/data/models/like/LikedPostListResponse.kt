package org.joseph.friendsync.data.models.like

import kotlinx.serialization.Serializable

@Serializable
data class LikedPostListResponse(
    val data: List<LikedPostCloud>? = null,
    val errorMessage: String? = null
)

@Serializable
data class LikedPostResponse(
    val data: LikedPostCloud? = null,
    val errorMessage: String? = null
)

@Serializable
data class LikedPostCloud(
    val id: Int,
    val postId: Int,
    val userId: Int,
)