package org.joseph.friendsync.data.local.dao.subscriptions

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.joseph.friendsync.common.util.coroutines.DispatcherProvider
import org.joseph.friendsync.data.local.mappers.SubscriptionSqlToSubscriptionLocalMapper
import org.joseph.friendsync.data.local.models.SubscriptionLocal
import org.joseph.friendsync.data.models.subscription.SubscriptionCloud
import org.joseph.friendsync.database.AppDatabase

class SubscriptionsDaoImpl(
    private val appDatabase: AppDatabase,
    private val dispatcherProvider: DispatcherProvider,
    private val subscriptionSqlToSubscriptionLocalMapper: SubscriptionSqlToSubscriptionLocalMapper
) : SubscriptionsDao {

    private val subscriptionsQueries by lazy { appDatabase.subscriptionsQueries }

    override suspend fun allSubscriptions(
        followerId: Long
    ): List<SubscriptionLocal> = withContext(dispatcherProvider.io) {
        subscriptionsQueries
            .allSubscriptions(followerId)
            .executeAsList()
            .map(subscriptionSqlToSubscriptionLocalMapper::map)
    }

    override fun reactiveAllSubscriptions(followerId: Long): Flow<List<SubscriptionLocal>> {
        return subscriptionsQueries
            .reactiveAllSubscriptions(followerId)
            .asFlow()
            .mapToList(dispatcherProvider.io)
            .map { subscriptions ->
                subscriptions.map(subscriptionSqlToSubscriptionLocalMapper::map)
            }.flowOn(dispatcherProvider.io)
    }

    override suspend fun subscriptionById(
        id: Long
    ): SubscriptionLocal? = withContext(dispatcherProvider.io) {
        val subscription = subscriptionsQueries.subscriptionById(id).executeAsOneOrNull()
        if (subscription == null) null
        else subscriptionSqlToSubscriptionLocalMapper.map(subscription)
    }

    override suspend fun deleteSubscriptionById(id: Long) = withContext(dispatcherProvider.io) {
        subscriptionsQueries.deleteSubscriptionByPostId(id)
    }

    override suspend fun insertOrUpdateSubscription(
        id: Int,
        followerId: Int,
        followingId: Int
    ) = withContext(dispatcherProvider.io) {
        subscriptionsQueries.insertOrUpdateSubscription(
            id.toLong(),
            followerId.toLong(),
            followingId.toLong()
        )
    }

    override suspend fun insertOrUpdateSubscriptions(subscriptions: List<SubscriptionCloud>) {
        subscriptions.forEach {
            insertOrUpdateSubscription(
                id = it.id,
                followingId = it.followingId,
                followerId = it.followerId
            )
        }
    }

    override suspend fun removeAllSubscriptions() = withContext(dispatcherProvider.io) {
        subscriptionsQueries.removeAllSubscriptions()
    }
}