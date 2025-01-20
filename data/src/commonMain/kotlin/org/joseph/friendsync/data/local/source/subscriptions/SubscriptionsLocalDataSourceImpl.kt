package org.joseph.friendsync.data.local.source.subscriptions

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import org.joseph.friendsync.core.DispatcherProvider
import org.joseph.friendsync.data.local.dao.subscriptions.SubscriptionsDao
import org.joseph.friendsync.data.mappers.SubscriptionDataToLocalMapper
import org.joseph.friendsync.data.models.SubscriptionData

internal class SubscriptionsLocalDataSourceImpl(
    private val subscriptionsDao: SubscriptionsDao,
    private val dispatcherProvider: DispatcherProvider,
    private val subscriptionDataToLocalMapper: SubscriptionDataToLocalMapper
) : SubscriptionsLocalDataSource {

    override suspend fun deleteSubscriptionById(id: Long) {
        return withContext(dispatcherProvider.io) {
            try {
                subscriptionsDao.deleteSubscriptionById(id)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to delete subscription from cache", e)
            }
        }
    }

    override suspend fun addSubscription(subscription: SubscriptionData) {
        return withContext(dispatcherProvider.io) {
            try {
                val subscriptionLocal = subscriptionDataToLocalMapper.map(subscription)
                subscriptionsDao.insertOrUpdateSubscription(subscriptionLocal)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to add subscription from cache", e)
            }
        }
    }

    override suspend fun addSubscriptions(subscriptions: List<SubscriptionData>) {
        return withContext(dispatcherProvider.io) {
            try {
                val subscriptionsLocal = subscriptions.map(subscriptionDataToLocalMapper::map)
                subscriptionsDao.insertOrUpdateSubscriptions(subscriptionsLocal)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to add subscriptions from cache", e)
            }
        }
    }

    override suspend fun removeAllSubscriptions() {
        return withContext(dispatcherProvider.io) {
            try {
                subscriptionsDao.removeAllSubscriptions()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw IllegalStateException("Failed to remove subscriptions from cache", e)
            }
        }
    }
}