package org.joseph.friendsync.data.models.user

import kotlinx.serialization.Serializable
import org.joseph.friendsync.domain.models.UserPreferences
import org.joseph.friendsync.domain.models.AuthResultData

@Serializable
data class UserPreferencesData(
    val id: Int = -1,
    val name: String = String(),
    val lastName: String = String(),
    val bio: String? = null,
    val avatar: String? = null,
    val token: String = String(),
) {

    fun isUnknown() = this.id == unknown.id

    companion object {
        val unknown = UserPreferencesData(
            id = -1,
            name = String(),
            bio = String(),
            avatar = null,
            token = String(),
        )
    }
}

fun UserPreferencesData.toAuthResultData(): AuthResultData {
    return AuthResultData(
        id = id,
        name = name,
        bio = bio,
        avatar = avatar,
        token = token,
        lastName = lastName
    )
}

fun UserPreferences.toData(): UserPreferencesData {
    return UserPreferencesData(
        id = id,
        name = name,
        bio = bio,
        avatar = avatar,
        token = token,
        lastName = lastName
    )
}

fun UserPreferencesData.toDomain(): UserPreferences {
    return UserPreferences(
        id = id,
        name = name,
        bio = bio,
        avatar = avatar,
        token = token,
        lastName = lastName
    )
}