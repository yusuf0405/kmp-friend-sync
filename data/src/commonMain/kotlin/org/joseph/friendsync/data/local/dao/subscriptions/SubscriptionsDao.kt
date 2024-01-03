package org.joseph.friendsync.data.local.dao.subscriptions

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.data.local.models.SubscriptionLocal
import org.joseph.friendsync.data.models.subscription.SubscriptionCloud

interface SubscriptionsDao {

    suspend fun allSubscriptions(followerId: Long): List<SubscriptionLocal>

    fun reactiveAllSubscriptions(followerId: Long): Flow<List<SubscriptionLocal>>

    suspend fun subscriptionById(id: Long): SubscriptionLocal?

    suspend fun deleteSubscriptionById(id: Long)

    suspend fun insertOrUpdateSubscription(id: Int, followerId: Int, followingId: Int)

    suspend fun insertOrUpdateSubscriptions(subscriptions: List<SubscriptionCloud>)

    suspend fun removeAllSubscriptions()
}
