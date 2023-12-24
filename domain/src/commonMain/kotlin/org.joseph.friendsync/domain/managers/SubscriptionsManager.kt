package org.joseph.friendsync.domain.managers

import kotlinx.coroutines.flow.Flow

interface SubscriptionsManager {

    val subscribeUserIdsFlow: Flow<List<Int>>

    suspend fun fetchSubscriptionUserIds()

    suspend fun cancelSubscription(userId: Int, onError: (String) -> Unit)

    suspend fun createSubscription(userId: Int, onError: (String) -> Unit)
}