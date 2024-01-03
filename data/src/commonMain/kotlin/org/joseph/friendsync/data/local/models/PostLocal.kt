package org.joseph.friendsync.data.local.models

data class PostLocal(
    val commentsCount: Int,
    val id: Int,
    val imageUrls: List<String>,
    val likesCount: Int,
    val message: String?,
    val releaseDate: Long,
    val savedCount: Int,
    val userId: Int,
    val userName: String,
    val userLastname: String,
    val userAvatar: String?,
    val userReleaseDate: Long,
)