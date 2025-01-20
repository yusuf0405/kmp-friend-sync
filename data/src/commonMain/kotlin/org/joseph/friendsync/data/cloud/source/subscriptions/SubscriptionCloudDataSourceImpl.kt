package org.joseph.friendsync.data.cloud.source.subscriptions

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.data.cloud.mappers.SubscriptionCloudToDataMapper
import org.joseph.friendsync.data.cloud.service.subscription.SubscriptionService
import org.joseph.friendsync.data.models.SubscriptionData

private const val UNKNOWN_USER_ID = -1

internal class SubscriptionCloudDataSourceImpl(
    private val subscriptionService: SubscriptionService,
    private val dispatcherProvider: DispatcherProvider,
    private val subscriptionCloudToDataMapper: SubscriptionCloudToDataMapper
) : SubscriptionCloudDataSource {

    override suspend fun createSubscription(
        followerId: Int,
        followingId: Int
    ): SubscriptionId = withContext(dispatcherProvider.io) {
        val defaultError = IllegalStateException("Failed to create subscription from cloud")
        try {
            val subscriptionId = subscriptionService.createSubscription(followerId, followingId)
                .data
                ?.subscriptionId
                ?: throw defaultError
            subscriptionId.apply {
                if (this == UNKNOWN_USER_ID) throw defaultError
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            throw defaultError
        }
    }

    override suspend fun cancelSubscription(
        followerId: Int,
        followingId: Int
    ): SubscriptionId = withContext(dispatcherProvider.io) {
        val defaultError = IllegalStateException("Failed to cancel subscription from cloud")
        try {
            val subscriptionId = subscriptionService.cancelSubscription(followerId, followingId)
                .data
                ?.subscriptionId
                ?: throw defaultError
            subscriptionId.apply {
                if (this == UNKNOWN_USER_ID) throw defaultError
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            throw defaultError
        }
    }

    override suspend fun fetchUserSubscriptions(userId: Int): List<SubscriptionData> {
        return withContext(dispatcherProvider.io) {
            try {
                val response = subscriptionService.fetchUserSubscriptions(userId)
                val subscriptionsCloud = response.data ?: emptyList()
                subscriptionsCloud.map(subscriptionCloudToDataMapper::map)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to create subscription from cloud", e)
            }
        }
    }

    override suspend fun fetchSubscriptionUserIds(userId: Int): List<UserId> {
        return withContext(dispatcherProvider.io) {
            try {
                val response = subscriptionService.fetchSubscriptionUserIds(userId)
                response.data?.userIds
                    ?: throw IllegalStateException("Failed to fetch subscriptions from cloud")
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to fetch subscriptions from cloud", e)
            }
        }
    }

    override suspend fun hasUserSubscription(
        userId: Int,
        followingId: Int
    ): ShouldUserSubscription = withContext(dispatcherProvider.io) {
        try {
            subscriptionService.hasUserSubscription(userId, followingId).data
                ?: throw IllegalStateException("Failed to create subscription from cloud")
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            throw IllegalStateException("Failed to create subscription from cloud", e)
        }
    }
}