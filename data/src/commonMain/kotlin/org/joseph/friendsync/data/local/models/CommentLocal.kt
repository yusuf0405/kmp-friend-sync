package org.joseph.friendsync.data.local.models

import kotlinx.serialization.Serializable
import org.joseph.friendsync.data.models.user.UserInfoCloud

@Serializable
data class CommentLocal(
    val id: Int,
    val comment: String,
    val postId: Int,
    val likesCount: Int,
    val releaseDate: Long,
    val userId: Int,
    val userName: String,
    val userLastname: String,
    val userAvatar: String?,
    val userReleaseDate: Long,
)