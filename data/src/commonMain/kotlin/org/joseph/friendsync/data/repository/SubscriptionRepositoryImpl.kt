package org.joseph.friendsync.data.repository

import kotlinx.coroutines.withContext
import org.joseph.friendsync.common.util.Result
import org.joseph.friendsync.common.util.coroutines.DispatcherProvider
import org.joseph.friendsync.common.util.coroutines.callSafe
import org.joseph.friendsync.common.util.filterNotNullOrError
import org.joseph.friendsync.common.util.map
import org.joseph.friendsync.data.service.SubscriptionService
import org.joseph.friendsync.domain.repository.SubscriptionRepository

internal class SubscriptionRepositoryImpl(
    private val service: SubscriptionService,
    private val dispatcherProvider: DispatcherProvider,
) : SubscriptionRepository {

    override suspend fun createSubscription(
        followerId: Int,
        followingId: Int
    ): Result<Int> = withContext(dispatcherProvider.io) {
        callSafe {
            service.createSubscription(
                followerId = followerId,
                followingId = followingId
            ).map { response ->
                response.data?.followingCount
            }.filterNotNullOrError()
        }
    }

    override suspend fun cancelSubscription(
        followerId: Int,
        followingId: Int
    ): Result<Int> = withContext(dispatcherProvider.io) {
        callSafe {
            service.cancelSubscription(
                followerId = followerId,
                followingId = followingId
            ).map { response ->
                response.data?.followingCount
            }.filterNotNullOrError()
        }
    }

    override suspend fun fetchSubscriptionUserIds(
        userId: Int
    ): Result<List<Int>> = withContext(dispatcherProvider.io) {
        callSafe {
            service.fetchSubscriptionUserIds(userId).map { response ->
                response.data?.userIds

            }.filterNotNullOrError()
        }
    }

    override suspend fun hasUserSubscription(
        userId: Int, followingId: Int
    ): Result<Boolean> = withContext(dispatcherProvider.io) {
        callSafe {
            service.hasUserSubscription(userId, followingId).map { response ->
                response.data
            }.filterNotNullOrError()
        }
    }
}