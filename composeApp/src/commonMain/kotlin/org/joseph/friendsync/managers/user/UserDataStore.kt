package org.joseph.friendsync.managers.user

import com.russhwolf.settings.Settings
import org.joseph.friendsync.domain.models.AuthResultData

expect fun createSettings(): Settings

interface UserDataStore {

    fun saveCurrentUser(authResultData: AuthResultData)

    fun removeCurrentUser(user: UserPreferences)

    fun fetchCurrentUser(): UserPreferences

    fun isUserAuthorized(): Boolean
}