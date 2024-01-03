package org.joseph.friendsync.data.models.subscription

import kotlinx.serialization.Serializable

@Serializable
data class SubscriptionIdResponse(
    val data: ResultSubscriptionId? = null,
    val errorMessage: String? = null
)

@Serializable
data class ResultSubscriptionId(
    val subscriptionId: Int,
)