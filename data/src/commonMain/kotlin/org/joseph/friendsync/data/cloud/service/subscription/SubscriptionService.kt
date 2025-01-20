package org.joseph.friendsync.data.cloud.service.subscription

import org.joseph.friendsync.data.models.subscription.ShouldUserSubscriptionResponse
import org.joseph.friendsync.data.models.subscription.SubscriptionIdResponse
import org.joseph.friendsync.data.models.subscription.SubscriptionIdsResponse
import org.joseph.friendsync.data.models.subscription.SubscriptionsResponse

internal interface SubscriptionService {

    suspend fun createSubscription(followerId: Int, followingId: Int): SubscriptionIdResponse

    suspend fun cancelSubscription(followerId: Int, followingId: Int): SubscriptionIdResponse

    suspend fun fetchUserSubscriptions(userId: Int): SubscriptionsResponse

    suspend fun fetchSubscriptionUserIds(userId: Int): SubscriptionIdsResponse

    suspend fun hasUserSubscription(userId: Int, followingId: Int): ShouldUserSubscriptionResponse
}