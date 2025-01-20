package org.joseph.friendsync.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "comment_table",
    indices = [Index(value = ["post_id"])]
)
data class CommentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("comment") val comment: String,
    @ColumnInfo("post_id") val postId: Int,
    @ColumnInfo("likes_count") val likesCount: Int,
    @ColumnInfo("release_date") val releaseDate: Long,
    @ColumnInfo("user_id") val userId: Int,
    @ColumnInfo("user_name") val userName: String,
    @ColumnInfo("user_lastname") val userLastname: String,
    @ColumnInfo("user_avatar") val userAvatar: String?,
    @ColumnInfo("user_release_date") val userReleaseDate: Long,
    @ColumnInfo("is_sync") val isSync: Boolean = false,
)