package org.joseph.friendsync.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "subscriptions_table",
    indices = [Index(value = ["follower_id"])]
)
data class SubscriptionLocal(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("follower_id") val followerId: Int,
    @ColumnInfo("following_id") val followingId: Int
)