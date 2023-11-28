package org.joseph.friendsync.managers.user

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValue
import com.russhwolf.settings.serialization.encodeValue
import com.russhwolf.settings.serialization.removeValue
import kotlinx.serialization.ExperimentalSerializationApi
import org.joseph.friendsync.domain.models.AuthResultData
import org.joseph.friendsync.mappers.AuthResultDataToUserPreferencesMapper
import org.joseph.friendsync.navigation.GlobalNavigationFlowCommunication
import org.joseph.friendsync.navigation.NavCommand

private const val CURRENT_USER_SETTING_KEY = "current_user"

@OptIn(ExperimentalSettingsApi::class, ExperimentalSerializationApi::class)
class UserDataStoreImpl(
    private val authResultDataToUserPreferencesMapper: AuthResultDataToUserPreferencesMapper,
    private val globalNavigationFlowCommunication: GlobalNavigationFlowCommunication,
) : UserDataStore {

    private val settings: Settings by lazy(LazyThreadSafetyMode.NONE) { createSettings() }

    override fun saveCurrentUser(authResultData: AuthResultData) {
        val userPreferences = authResultDataToUserPreferencesMapper.map(authResultData)
        settings.encodeValue(
            UserPreferences.serializer(),
            CURRENT_USER_SETTING_KEY,
            userPreferences,
        )
    }

    override fun removeCurrentUser(user: UserPreferences) {
        settings.removeValue(
            UserPreferences.serializer(),
            CURRENT_USER_SETTING_KEY,
            ignorePartial = true
        )
    }

    override fun fetchCurrentUser(): UserPreferences {
        val user = settings.decodeValue(
            UserPreferences.serializer(),
            CURRENT_USER_SETTING_KEY,
            UserPreferences.unknown
        )
        if (user.isUnknown()) globalNavigationFlowCommunication.emit(NavCommand.Auth)
        return user
    }

    override fun isUserAuthorized(): Boolean {
        return !fetchCurrentUser().isUnknown()
    }
}