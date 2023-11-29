package org.joseph.friendsync.data.repository

import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.callSafe
import org.joseph.friendsync.data.service.SubscriptionService
import org.joseph.friendsync.domain.repository.SubscriptionRepository

internal class SubscriptionRepositoryImpl constructor(
    private val service: SubscriptionService,
) : SubscriptionRepository {

    override suspend fun createSubscription(
        followerId: Int,
        followingId: Int
    ): Result<Int> = callSafe(
        Result.Error(message = defaultErrorMessage)
    ) {
        val response = service.createSubscription(
            followerId = followerId,
            followingId = followingId
        )
        Result.Success(data = response.data!!.followingCount)
    }

    override suspend fun cancelSubscription(
        followerId: Int,
        followingId: Int
    ): Result<Int> = callSafe(
        Result.Error(message = defaultErrorMessage)
    ) {
        val response = service.cancelSubscription(
            followerId = followerId,
            followingId = followingId
        )
        Result.Success(data = response.data!!.followingCount)
    }

    override suspend fun fetchSubscriptionUserIds(
        userId: Int
    ): Result<List<Int>> = callSafe(
        Result.Error(message = defaultErrorMessage)
    ) {
        val response = service.fetchSubscriptionUserIds(userId)
        val userIds = response.data!!.userIds
        Result.Success(data = userIds)
    }
}