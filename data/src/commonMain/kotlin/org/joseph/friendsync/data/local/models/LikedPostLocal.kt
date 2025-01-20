package org.joseph.friendsync.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "like_post_table",
    indices = [Index(value = ["user_id"])]
)
data class LikedPostLocal(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("post_id") val postId: Int,
    @ColumnInfo("user_id") val userId: Int,
)
