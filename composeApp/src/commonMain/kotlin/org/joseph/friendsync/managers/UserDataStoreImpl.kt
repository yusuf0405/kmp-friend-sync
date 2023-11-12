package org.joseph.friendsync.managers

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.serialization.containsValue
import com.russhwolf.settings.serialization.decodeValue
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import com.russhwolf.settings.serialization.nullableSerializedValue
import com.russhwolf.settings.serialization.removeValue
import kotlinx.serialization.ExperimentalSerializationApi
import org.joseph.friendsync.domain.models.AuthResultData
import org.joseph.friendsync.mappers.AuthResultDataToUserPreferencesMapper

private const val CURRENT_USER_SETTING_KEY = "current_user"

@OptIn(ExperimentalSettingsApi::class, ExperimentalSerializationApi::class)
class UserDataStoreImpl(
    private val authResultDataToUserPreferencesMapper: AuthResultDataToUserPreferencesMapper
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
        return settings.decodeValue(
            UserPreferences.serializer(), CURRENT_USER_SETTING_KEY,
            UserPreferences.unknown
        )
    }

    override fun isUserAuthorized(): Boolean {
        return !settings.containsValue(
            UserPreferences.serializer(),
            CURRENT_USER_SETTING_KEY
        )
    }
}