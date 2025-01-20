package org.joseph.friendsync.data.cloud.models

import kotlinx.serialization.Serializable

@Serializable
data class UserPersonalInfoResponse(
    val data: UserPersonalInfoCloud? = null,
    val errorMessage: String? = null
)

@Serializable
data class UserPersonalInfoCloud(
    val id: Int,
    val email: String,
)