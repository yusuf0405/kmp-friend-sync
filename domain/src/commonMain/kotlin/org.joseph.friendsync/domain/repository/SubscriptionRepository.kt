package org.joseph.friendsync.domain.repository

import org.joseph.friendsync.common.util.Result

interface SubscriptionRepository {

    suspend fun createSubscription(followerId: Int, followingId: Int): Result<Int>

    suspend fun cancelSubscription(followerId: Int, followingId: Int): Result<Int>

    suspend fun fetchSubscriptionUserIds(userId: Int): Result<List<Int>>

    suspend fun hasUserSubscription(userId: Int, followingId: Int): Result<Boolean>
}