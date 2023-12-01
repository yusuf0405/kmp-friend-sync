package org.joseph.friendsync.data.models.user

import kotlinx.serialization.Serializable

@Serializable
data class EditProfileParamsResponse(
    val data: EditProfileParamsCloud? = null,
    val errorMessage: String? = null
)

@Serializable
data class EditProfileParamsCloud(
    val id: Int,
    val name: String,
    val lastName: String,
    val email: String,
    val bio: String,
    val education: String,
    val avatar: String,
)