package org.joseph.friendsync.data.local.dao.subscriptions

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.joseph.friendsync.data.local.models.SubscriptionLocal

@Dao
interface SubscriptionsDao {

    @Query("SELECT * FROM subscriptions_table WHERE follower_id = :followerId")
    suspend fun allSubscriptions(followerId: Long): List<SubscriptionLocal>

    @Query("SELECT * FROM subscriptions_table WHERE follower_id = :followerId")
    fun reactiveAllSubscriptions(followerId: Long): Flow<List<SubscriptionLocal>>

    @Query("SELECT * FROM subscriptions_table WHERE id = :id LIMIT 1")
    suspend fun subscriptionById(id: Long): SubscriptionLocal?

    @Query("DELETE FROM subscriptions_table WHERE id = :id")
    suspend fun deleteSubscriptionById(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateSubscription(subscription: SubscriptionLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateSubscriptions(subscriptions: List<SubscriptionLocal>)

    @Query("DELETE FROM subscriptions_table")
    suspend fun removeAllSubscriptions()
}
