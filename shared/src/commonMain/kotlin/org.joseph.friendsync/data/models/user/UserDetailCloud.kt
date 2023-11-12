package org.joseph.friendsync.data.models.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDetailResponse(
    @SerialName("data")
    val data: UserDetailCloud? = null,
    @SerialName("errorMessage")
    val errorMessage: String? = null
)

@Serializable
data class UserDetailCloud(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("lastName")
    val lastName: String,
    @SerialName("bio")
    val bio: String?,
    @SerialName("avatar")
    val avatar: String?,
    @SerialName("profileBackground")
    val profileBackground: String?,
    @SerialName("education")
    val education: String?,
    @SerialName("releaseDate")
    val releaseDate: Long,
    @SerialName("followersCount")
    val followersCount: Int,
    @SerialName("followingCount")
    val followingCount: Int
)