package org.joseph.friendsync.data.models.subscription

import kotlinx.serialization.Serializable

@Serializable
data class CreateOrCancelSubscription(
    val followerId: Int,
    val followingId: Int
)