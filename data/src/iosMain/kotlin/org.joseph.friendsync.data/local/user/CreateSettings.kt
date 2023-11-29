package org.joseph.friendsync.data.local.user

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.joseph.friendsync.PlatformConfiguration
import platform.Foundation.NSUserDefaults

actual class CreateSettings actual constructor(
    private val platformConfiguration: PlatformConfiguration
) {

    actual fun createSettings(): Settings {
        val delegate = NSUserDefaults.standardUserDefaults
        return NSUserDefaultsSettings(delegate)
    }
}