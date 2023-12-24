package org.joseph.friendsync.data.models.user

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
) {
    companion object {

        val unknown = UserPersonalInfoCloud(
            id = -1,
            email = String(),
        )
    }
}