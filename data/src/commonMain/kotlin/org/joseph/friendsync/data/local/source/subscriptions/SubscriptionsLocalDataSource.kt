package org.joseph.friendsync.data.local.source.subscriptions

import org.joseph.friendsync.data.models.SubscriptionData

internal interface SubscriptionsLocalDataSource {

    suspend fun deleteSubscriptionById(id: Long)

    suspend fun addSubscription(subscription: SubscriptionData)

    suspend fun addSubscriptions(subscriptions: List<SubscriptionData>)

    suspend fun removeAllSubscriptions()
}