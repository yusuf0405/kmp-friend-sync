package org.joseph.friendsync.domain.models

data class AddCommentParams(
    val userId: Int,
    val postId: Int,
    val commentText: String
)