package org.joseph.friendsync.domain.models

data class CurrentUserDomain(
    val id: Int,
    val name: String,
    val lastName: String,
    val bio: String?,
    val avatar: String?,
    val profileBackground: String?,
    val education: String?,
    val email: String?,
    val releaseDate: Long,
    val followersCount: Int,
    val followingCount: Int
)