package org.joseph.friendsync.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "posts_table",
    indices = [
        Index(value = ["release_date"]),
        Index(value = ["user_id"])
    ]
)
data class PostEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("comments_count") val commentsCount: Int,

    // TODO: Поправить как будет возмозносить
//    @ColumnInfo("image_urls") val imageUrls: List<String>,
    @ColumnInfo("likes_count") val likesCount: Int,
    @ColumnInfo("message") val message: String?,
    @ColumnInfo("release_date") val releaseDate: Long,
    @ColumnInfo("saved_count") val savedCount: Int,
    @ColumnInfo("user_id") val userId: Int,
    @ColumnInfo("user_name") val userName: String,
    @ColumnInfo("user_lastname") val userLastname: String,
    @ColumnInfo("user_avatar") val userAvatar: String?,
    @ColumnInfo("user_release_date") val userReleaseDate: Long,
    @ColumnInfo("is_recommended") val isRecommended: Boolean = false,
)