package org.joseph.friendsync.data.models

data class PostData(
    val commentsCount: Int,
    val id: Int,
    val imageUrls: List<String>,
    val likesCount: Int,
    val message: String?,
    val releaseDate: Long,
    val savedCount: Int,
    val user: UserInfoData
)