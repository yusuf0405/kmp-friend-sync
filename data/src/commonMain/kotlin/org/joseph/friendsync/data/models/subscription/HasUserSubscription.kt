package org.joseph.friendsync.data.models.subscription

import kotlinx.serialization.Serializable

@Serializable
data class ShouldUserSubscriptionResponse(
    val data: Boolean? = null,
    val errorMessage: String? = null
)