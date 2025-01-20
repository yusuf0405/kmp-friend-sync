package org.joseph.friendsync.domain.repository

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.core.Result
import org.joseph.friendsync.domain.models.SubscriptionDomain

typealias UserId = Int
typealias HasUserSubscription = Boolean

interface SubscriptionRepository {

    fun observeUserSubscriptions(userId: Int): Flow<List<SubscriptionDomain>>

    suspend fun createSubscription(followerId: Int, followingId: Int)

    suspend fun cancelSubscription(followerId: Int, followingId: Int)

    suspend fun fetchSubscriptionUserIds(userId: Int): List<UserId>

    suspend fun fetchUserSubscriptions(userId: Int): List<SubscriptionDomain>

    suspend fun hasUserSubscription(userId: Int, followingId: Int): HasUserSubscription

    suspend fun removeAllSubscriptionsInLocalDB()
}