package org.joseph.friendsync.domain.models


data class PostDomain(
    val commentsCount: Int,
    val id: Int,
    val imageUrls: List<String>,
    val likesCount: Int,
    val message: String?,
    val releaseDate: Long,
    val savedCount: Int,
    val user: PostUserDomain
)

data class PostUserDomain(
    val id: Int?,
    val name: String?,
    val lastName: String?,
    val userImage: String?,
)