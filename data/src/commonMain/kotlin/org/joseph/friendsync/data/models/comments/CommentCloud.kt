package org.joseph.friendsync.data.models.comments

import kotlinx.serialization.Serializable
import org.joseph.friendsync.data.models.user.UserInfoCloud

@Serializable
data class CommentCloud(
    val id: Int,
    val comment: String,
    val postId: Int,
    val likesCount: Int,
    val user: UserInfoCloud,
    val releaseDate: Long,
)

@Serializable
data class CommentResponse(
    val data: CommentCloud? = null,
    val errorMessage: String? = null
)

@Serializable
data class CommentListResponse(
    val data: List<CommentCloud>? = null,
    val errorMessage: String? = null
)