package org.joseph.friendsync.data.local.user

import com.russhwolf.settings.Settings
import org.joseph.friendsync.PlatformConfiguration

expect class CreateSettings constructor(platformConfiguration: PlatformConfiguration) {

    fun createSettings(): Settings
}