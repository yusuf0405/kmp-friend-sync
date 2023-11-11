package org.joseph.friendsync.data.models.post


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    val data: PostCloud? = null,
    val errorMessage: String? = null
)

@Serializable
data class PostListResponse(
    val data: List<PostCloud>? = null,
    val errorMessage: String? = null
)

@Serializable
data class PostCloud(
    @SerialName("commentsCount")
    val commentsCount: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("imageUrls")
    val imageUrls: List<String>,
    @SerialName("likesCount")
    val likesCount: Int,
    @SerialName("message")
    val message: String?,
    @SerialName("releaseDate")
    val releaseDate: Long,
    @SerialName("savedCount")
    val savedCount: Int,
    @SerialName("user")
    val user: PostUserCloud
)

@Serializable
data class PostUserCloud(
    val id: Int?,
    val name: String?,
    val lastName: String?,
    val userImage: String?,
)