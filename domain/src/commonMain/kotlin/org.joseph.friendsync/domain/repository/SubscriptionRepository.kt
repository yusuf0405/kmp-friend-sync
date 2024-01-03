package org.joseph.friendsync.domain.repository

import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.domain.models.SubscriptionDomain

interface SubscriptionRepository {

    suspend fun createSubscription(followerId: Int, followingId: Int): Result<Unit>

    suspend fun cancelSubscription(followerId: Int, followingId: Int): Result<Unit>

    suspend fun fetchSubscriptionUserIds(userId: Int): Result<List<Int>>

    suspend fun fetchUserSubscriptions(userId: Int): Result<List<SubscriptionDomain>>

    suspend fun removeAllSubscriptionsInLocalDB()

    fun observeUserSubscriptions(userId: Int): Flow<List<SubscriptionDomain>>

    suspend fun hasUserSubscription(userId: Int, followingId: Int): Result<Boolean>
}