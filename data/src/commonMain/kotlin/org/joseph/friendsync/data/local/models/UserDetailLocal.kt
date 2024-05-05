package org.joseph.friendsync.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    tableName = "users_table",
    indices = [
        Index(value = ["release_date"]),
        Index(value = ["followers_count"]),
        Index(value = ["following_count"])
    ]
)
data class UserDetailLocal(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("last_name") val lastName: String,
    @ColumnInfo("bio") val bio: String?,
    @ColumnInfo("avatar") val avatar: String?,
    @ColumnInfo("profile_background") val profileBackground: String?,
    @ColumnInfo("education") val education: String?,
    @ColumnInfo("release_date") val releaseDate: Long,
    @ColumnInfo("followers_count") val followersCount: Int,
    @ColumnInfo("following_count") val followingCount: Int
)