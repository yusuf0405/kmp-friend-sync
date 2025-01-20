package org.joseph.friendsync.data.models

import kotlinx.serialization.Serializable

@Serializable
data class EditProfileParamsData(
    val id: Int,
    val name: String,
    val lastName: String,
    val email: String,
    val bio: String,
    val education: String,
    val avatar: String,
)