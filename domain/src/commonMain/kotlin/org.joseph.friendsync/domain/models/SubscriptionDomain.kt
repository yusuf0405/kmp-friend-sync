package org.joseph.friendsync.domain.models

data class SubscriptionDomain(
    val id: Int,
    val followerId: Int,
    val followingId: Int
)