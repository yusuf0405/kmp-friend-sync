package org.joseph.friendsync.data.local.models

data class UserDetailLocal(
    val id: Int,
    val name: String,
    val lastName: String,
    val bio: String?,
    val avatar: String?,
    val profileBackground: String?,
    val education: String?,
    val releaseDate: Long,
    val followersCount: Int,
    val followingCount: Int
)