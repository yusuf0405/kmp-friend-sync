package org.joseph.friendsync.data.local.source.subscriptions

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.data.models.SubscriptionData

internal interface SubscriptionsReadLocalDataSource {

    fun observeAllSubscriptions(followerId: Long): Flow<List<SubscriptionData>>

    suspend fun getAllSubscriptions(followerId: Long): List<SubscriptionData>

    suspend fun getSubscriptionById(id: Long): SubscriptionData
}