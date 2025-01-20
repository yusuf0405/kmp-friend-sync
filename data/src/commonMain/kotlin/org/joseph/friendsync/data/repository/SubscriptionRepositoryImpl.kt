package org.joseph.friendsync.data.repository

import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joseph.friendsync.data.cloud.source.subscriptions.SubscriptionCloudDataSource
import org.joseph.friendsync.data.local.source.subscriptions.SubscriptionsLocalDataSource
import org.joseph.friendsync.data.local.source.subscriptions.SubscriptionsReadLocalDataSource
import org.joseph.friendsync.data.local.source.users.UserLocalDataSource
import org.joseph.friendsync.data.mappers.SubscriptionDataToDomainMapper
import org.joseph.friendsync.data.models.SubscriptionData
import org.joseph.friendsync.domain.models.SubscriptionDomain
import org.joseph.friendsync.domain.repository.HasUserSubscription
import org.joseph.friendsync.domain.repository.SubscriptionRepository
import org.joseph.friendsync.domain.repository.UserId

internal class SubscriptionRepositoryImpl(
    private val subscriptionCloudDataSource: SubscriptionCloudDataSource,
    private val subscriptionsLocalDataSource: SubscriptionsLocalDataSource,
    private val subscriptionsReadLocalDataSource: SubscriptionsReadLocalDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val subscriptionDataToDomainMapper: SubscriptionDataToDomainMapper
) : SubscriptionRepository {

    override fun observeUserSubscriptions(userId: Int): Flow<List<SubscriptionDomain>> {
        return subscriptionsReadLocalDataSource
            .observeAllSubscriptions(userId.toLong())
            .map { subscriptions -> subscriptions.map(subscriptionDataToDomainMapper::map) }
    }

    override suspend fun createSubscription(followerId: Int, followingId: Int) {
        val subscriptionId = subscriptionCloudDataSource.createSubscription(followerId, followingId)
        withContext(NonCancellable) {
            val subscriptionData = SubscriptionData(
                id = subscriptionId,
                followerId = followerId,
                followingId = followingId
            )
            subscriptionsLocalDataSource.addSubscription(subscriptionData)
            userLocalDataSource.incrementDecrementFollowersCount(followerId, true)
        }
    }

    override suspend fun cancelSubscription(followerId: Int, followingId: Int) {
        val subscriptionId = subscriptionCloudDataSource.cancelSubscription(followerId, followingId)
        withContext(NonCancellable) {
            subscriptionsLocalDataSource.deleteSubscriptionById(subscriptionId.toLong())
            userLocalDataSource.incrementDecrementFollowersCount(followerId, false)
        }
    }

    override suspend fun fetchUserSubscriptions(userId: Int): List<SubscriptionDomain> {
        val subscriptionsData = subscriptionCloudDataSource.fetchUserSubscriptions(userId)
        withContext(NonCancellable) {
            subscriptionsLocalDataSource.addSubscriptions(subscriptionsData)
        }
        return subscriptionsData.map(subscriptionDataToDomainMapper::map)
    }

    override suspend fun removeAllSubscriptionsInLocalDB() {
        subscriptionsLocalDataSource.removeAllSubscriptions()
    }

    override suspend fun fetchSubscriptionUserIds(userId: Int): List<UserId> {
        return subscriptionCloudDataSource.fetchSubscriptionUserIds(userId)
    }

    override suspend fun hasUserSubscription(userId: Int, followingId: Int): HasUserSubscription {
        return subscriptionCloudDataSource.hasUserSubscription(userId, followingId)
    }
}