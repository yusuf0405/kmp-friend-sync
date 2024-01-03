package org.joseph.friendsync.data.local.models

data class SubscriptionLocal(
    val id: Int,
    val followerId: Int,
    val followingId: Int
)