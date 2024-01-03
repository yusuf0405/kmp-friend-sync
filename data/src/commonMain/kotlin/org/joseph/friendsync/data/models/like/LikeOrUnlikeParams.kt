package org.joseph.friendsync.data.models.like

import kotlinx.serialization.Serializable

@Serializable
data class LikeOrUnlikeParams(
    val userId: Int,
    val postId: Int,
)