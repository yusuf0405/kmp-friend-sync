package org.joseph.friendsync.data.local.user

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValue
import com.russhwolf.settings.serialization.encodeValue
import com.russhwolf.settings.serialization.removeValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.ExperimentalSerializationApi
import org.joseph.friendsync.PlatformConfiguration
import org.joseph.friendsync.common.user.UserDataStore
import org.joseph.friendsync.common.user.UserPreferences

private const val CURRENT_USER_SETTING_KEY = "current_user"

@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
class UserDataStoreImpl(
    private val platformConfiguration: PlatformConfiguration
) : UserDataStore {

    private val userPreferencesFlow = MutableStateFlow(UserPreferences.unknown)

    private val settings: Settings by lazy(LazyThreadSafetyMode.NONE) {
        CreateSettings(platformConfiguration).createSettings()
    }

    override fun saveCurrentUser(user: UserPreferences) {
        settings.encodeValue(
            UserPreferences.serializer(),
            CURRENT_USER_SETTING_KEY,
            user,
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

        return settings.decodeValue(
            UserPreferences.serializer(),
            CURRENT_USER_SETTING_KEY,
            UserPreferences.unknown
        )
    }

    override fun observeCurrentUser(): Flow<UserPreferences> {
        return userPreferencesFlow.asStateFlow()
    }

    override fun isUserAuthorized(): Boolean {
        return !fetchCurrentUser().isUnknown()
    }
}