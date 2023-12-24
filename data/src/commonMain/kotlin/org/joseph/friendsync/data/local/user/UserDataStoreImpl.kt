package org.joseph.friendsync.data.local.user

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.decodeValue
import com.russhwolf.settings.serialization.encodeValue
import com.russhwolf.settings.serialization.removeValue
import kotlinx.serialization.ExperimentalSerializationApi
import org.joseph.friendsync.PlatformConfiguration
import org.joseph.friendsync.common.user.UserDataStore
import org.joseph.friendsync.common.user.UserPreferences

private const val CURRENT_USER_SETTING_KEY = "current_user"
const val UNKNOWN_USER_ID = -1
@OptIn(ExperimentalSerializationApi::class, ExperimentalSettingsApi::class)
class UserDataStoreImpl(
    private val platformConfiguration: PlatformConfiguration
) : UserDataStore {

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

    override fun isUserAuthorized(): Boolean {
        return !fetchCurrentUser().isUnknown()
    }
}