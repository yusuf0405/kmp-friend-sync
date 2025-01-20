package org.joseph.friendsync.data.cloud.models

import kotlinx.serialization.Serializable

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