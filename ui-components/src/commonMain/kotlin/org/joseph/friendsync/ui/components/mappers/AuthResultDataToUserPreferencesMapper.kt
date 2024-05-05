package org.joseph.friendsync.ui.components.mappers

import org.joseph.friendsync.core.Mapper
import org.joseph.friendsync.domain.models.AuthResultData
import org.joseph.friendsync.domain.models.UserPreferences

class AuthResultDataToUserPreferencesMapper : Mapper<AuthResultData, UserPreferences> {
    override fun map(from: AuthResultData): UserPreferences = from.run {
        UserPreferences(
            id = id,
            name = name,
            bio = bio,
            avatar = avatar,
            token = token,
        )
    }
}