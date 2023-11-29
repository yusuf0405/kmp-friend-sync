package org.joseph.friendsync.data.models.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoListResponse(
    @SerialName("data")
    val data: List<UserInfoCloud>? = null,
    @SerialName("errorMessage")
    val errorMessage: String? = null
)

@Serializable
data class UserInfoCloud(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("lastName")
    val lastName: String,
    @SerialName("avatar")
    val avatar: String?,
    @SerialName("releaseDate")
    val releaseDate: Long,
)
