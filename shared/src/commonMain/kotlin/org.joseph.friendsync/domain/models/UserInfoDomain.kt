package org.joseph.friendsync.domain.models

data class UserInfoDomain(
    val id: Int,
    val name: String,
    val lastName: String,
    val avatar: String?,
    val releaseDate: Long,
)
