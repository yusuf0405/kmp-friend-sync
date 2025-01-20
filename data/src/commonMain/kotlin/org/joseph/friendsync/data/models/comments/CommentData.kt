package org.joseph.friendsync.data.models.comments

import org.joseph.friendsync.data.models.UserInfoData

data class CommentData(
    val id: Int,
    val comment: String,
    val postId: Int,
    val likesCount: Int,
    val user: UserInfoData,
    val releaseDate: Long,
)