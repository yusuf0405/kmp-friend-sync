package org.joseph.friendsync.managers

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.joseph.friendsync.AndroidApp

private const val DATA_STORE_NAME = "friend_sync"

actual fun createSettings(): Settings {
    val delegate = AndroidApp.INSTANCE.getSharedPreferences(
        DATA_STORE_NAME,
        Context.MODE_PRIVATE
    )
    return SharedPreferencesSettings(delegate)
}
