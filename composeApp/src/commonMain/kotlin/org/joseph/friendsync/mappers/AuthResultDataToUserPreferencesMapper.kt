package org.joseph.friendsync.mappers

import org.joseph.friendsync.common.mapper.Mapper
import org.joseph.friendsync.domain.models.AuthResultData
import org.joseph.friendsync.managers.UserPreferences

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