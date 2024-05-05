package org.joseph.friendsync.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.Result.Companion.defaultError
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.core.extensions.callSafe
import org.joseph.friendsync.core.filterNotNullOrError
import org.joseph.friendsync.core.map
import org.joseph.friendsync.data.local.dao.subscriptions.SubscriptionsDao
import org.joseph.friendsync.data.local.dao.user.UserDao
import org.joseph.friendsync.data.local.dao.user.CurrentUserDao
import org.joseph.friendsync.data.mappers.SubscriptionCloudToSubscriptionDomainMapper
import org.joseph.friendsync.data.mappers.SubscriptionLocalToSubscriptionDomainMapper
import org.joseph.friendsync.data.service.SubscriptionService
import org.joseph.friendsync.domain.models.SubscriptionDomain
import org.joseph.friendsync.domain.repository.SubscriptionRepository
import org.joseph.friendsync.core.Result

private const val UNKNOWN_USER_ID = -1

internal class SubscriptionRepositoryImpl(
    private val service: SubscriptionService,
    private val subscriptionsDao: SubscriptionsDao,
    private val userDao: UserDao,
    private val currentUserDao: CurrentUserDao,
    private val dispatcherProvider: DispatcherProvider,
    private val subscriptionCloudToDomainMapper: SubscriptionCloudToSubscriptionDomainMapper,
    private val subscriptionLocalToDomainMapper: SubscriptionLocalToSubscriptionDomainMapper
) : SubscriptionRepository {

    override suspend fun createSubscription(
        followerId: Int,
        followingId: Int
    ): Result<Unit> = withContext(dispatcherProvider.io) {
        callSafe {
            val response = service.createSubscription(
                followerId = followerId,
                followingId = followingId
            )
            if (response.isSuccess()) {
                val postId = response.data?.data?.subscriptionId ?: return@callSafe defaultError()
                if (postId == UNKNOWN_USER_ID) return@callSafe defaultError()
//                subscriptionsDao.insertOrUpdateSubscription(
//                    id = postId,
//                    followerId = followerId,
//                    followingId = followingId
//                )
                userDao.incrementDecrementFollowersCount(followingId, true)
                currentUserDao.incrementDecrementFollowingCount(followerId, true)
            }
            response.map {}
        }
    }

    override suspend fun cancelSubscription(
        followerId: Int,
        followingId: Int
    ): Result<Unit> = withContext(dispatcherProvider.io) {
        callSafe {
            val response = service.cancelSubscription(
                followerId = followerId,
                followingId = followingId
            )
            if (response.isSuccess()) {
                val postId = response.data?.data?.subscriptionId ?: return@callSafe defaultError()
                if (postId == UNKNOWN_USER_ID) return@callSafe defaultError()
                subscriptionsDao.deleteSubscriptionById(id = postId.toLong())
                userDao.incrementDecrementFollowersCount(followingId, false)
                currentUserDao.incrementDecrementFollowingCount(followerId, false)
            }
            response.map {}
        }
    }

    override suspend fun fetchUserSubscriptions(
        userId: Int
    ): Result<List<SubscriptionDomain>> = withContext(dispatcherProvider.io) {
        callSafe {
            when (val response = service.fetchUserSubscriptions(userId)) {
                is Result.Success -> {
                    val subscriptionsCloud = response.data?.data ?: emptyList()
//                    subscriptionsDao.insertOrUpdateSubscriptions(subscriptionsCloud)
                    val subscriptions = subscriptionsCloud.map(subscriptionCloudToDomainMapper::map)
                    Result.Success(subscriptions)
                }

                is Result.Error -> {
                    defaultError()
                }
            }
        }
    }

    override suspend fun removeAllSubscriptionsInLocalDB() {
        subscriptionsDao.removeAllSubscriptions()
    }

    override fun observeUserSubscriptions(
        userId: Int
    ): Flow<List<SubscriptionDomain>> {
        return subscriptionsDao.reactiveAllSubscriptions(userId.toLong()).map { subscriptions ->
            subscriptions.map(subscriptionLocalToDomainMapper::map)
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