package org.joseph.friendsync.data.local.user

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.joseph.friendsync.PlatformConfiguration

private const val DATA_STORE_NAME = "friend_sync"

actual class CreateSettings actual constructor(
    private val platformConfiguration: PlatformConfiguration
) {

    actual fun createSettings(): Settings {
        val delegate = platformConfiguration.androidContext.getSharedPreferences(
            DATA_STORE_NAME,
            Context.MODE_PRIVATE
        )
        return SharedPreferencesSettings(delegate)
    }

}