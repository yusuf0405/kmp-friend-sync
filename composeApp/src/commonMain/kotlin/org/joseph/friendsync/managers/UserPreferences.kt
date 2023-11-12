package org.joseph.friendsync.managers

import kotlinx.serialization.Serializable
import org.joseph.friendsync.domain.models.AuthResultData

@Serializable
data class UserPreferences(
    val id: Int = -1,
    val name: String = String(),
    val lastName: String = String(),
    val bio: String? = null,
    val avatar: String? = null,
    val token: String = String(),
) {

    fun isUnknown() = this.id == unknown.id

    companion object {
        val unknown = UserPreferences(
            id = -1,
            name = String(),
            bio = String(),
            avatar = null,
            token = String(),
        )
    }
}

fun UserPreferences.toAuthResultData(): AuthResultData {
    return AuthResultData(
        id = id,
        name = name,
        bio = bio,
        avatar = avatar,
        token = token,
        lastName = lastName
    )
}