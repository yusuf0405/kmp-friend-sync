package org.joseph.friendsync.domain.models

data class LikedPostDomain(
    val id: Int,
    val postId: Int,
    val userId: Int,
)