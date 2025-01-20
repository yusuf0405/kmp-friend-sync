package org.joseph.friendsync.data.cloud.source.subscriptions

import org.joseph.friendsync.data.models.SubscriptionData

internal typealias UserId = Int
internal typealias ShouldUserSubscription = Boolean
internal typealias SubscriptionId = Int

internal interface SubscriptionCloudDataSource {

    suspend fun createSubscription(followerId: Int, followingId: Int): SubscriptionId

    suspend fun cancelSubscription(followerId: Int, followingId: Int): SubscriptionId

    suspend fun fetchUserSubscriptions(userId: Int): List<SubscriptionData>

    suspend fun fetchSubscriptionUserIds(userId: Int): List<UserId>

    suspend fun hasUserSubscription(userId: Int, followingId: Int): ShouldUserSubscription
}