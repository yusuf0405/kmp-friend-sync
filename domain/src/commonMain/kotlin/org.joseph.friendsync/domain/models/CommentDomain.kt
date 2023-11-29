package org.joseph.friendsync.domain.models

data class CommentDomain(
    val id: Int,
    val comment: String,
    val postId: Int,
    val likesCount: Int,
    val user: UserInfoDomain,
    val releaseDate: Long,
)