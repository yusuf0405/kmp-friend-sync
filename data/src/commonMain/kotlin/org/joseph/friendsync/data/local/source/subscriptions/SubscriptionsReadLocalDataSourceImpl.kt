package org.joseph.friendsync.data.local.source.subscriptions

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.data.local.dao.subscriptions.SubscriptionsDao
import org.joseph.friendsync.data.local.mappers.SubscriptionLocalToDataMapper
import org.joseph.friendsync.data.models.SubscriptionData

internal class SubscriptionsReadLocalDataSourceImpl(
    private val subscriptionsDao: SubscriptionsDao,
    private val dispatcherProvider: DispatcherProvider,
    private val subscriptionLocalToDataMapper: SubscriptionLocalToDataMapper
) : SubscriptionsReadLocalDataSource {

    override fun observeAllSubscriptions(
        followerId: Long
    ): Flow<List<SubscriptionData>> = subscriptionsDao
        .observeAllSubscriptions(followerId)
        .map { subscriptionsLocal -> subscriptionsLocal.map(subscriptionLocalToDataMapper::map) }
        .flowOn(dispatcherProvider.io)
        .catch {
            throw IllegalStateException("Failed to get subscriptions from cache", it)
        }

    override suspend fun getAllSubscriptions(followerId: Long): List<SubscriptionData> {
        return withContext(dispatcherProvider.io) {
            try {
                val subscriptionsLocal = subscriptionsDao.getAllSubscriptions(followerId)
                subscriptionsLocal.map(subscriptionLocalToDataMapper::map)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to get subscriptions from cache", e)
            }
        }
    }

    override suspend fun getSubscriptionById(id: Long): SubscriptionData {
        return withContext(dispatcherProvider.io) {
            try {
                val subscriptionLocal = subscriptionsDao.getSubscriptionById(id)
                    ?: throw IllegalStateException("Failed to get subscription from cache")
                subscriptionLocalToDataMapper.map(subscriptionLocal)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to get subscription from cache", e)
            }
        }
    }
}