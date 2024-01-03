package org.joseph.friendsync.data.models.subscription

import kotlinx.serialization.Serializable

@Serializable
data class SubscriptionsResponse(
    val data: List<SubscriptionCloud>? = null,
    val errorMessage: String? = null
)


@Serializable
data class SubscriptionCloud(
    val id: Int,
    val followerId: Int,
    val followingId: Int
)