package org.joseph.friendsync.data.models

data class UserInfoData(
    val id: Int,
    val name: String,
    val lastName: String,
    val avatar: String?,
    val releaseDate: Long
)
