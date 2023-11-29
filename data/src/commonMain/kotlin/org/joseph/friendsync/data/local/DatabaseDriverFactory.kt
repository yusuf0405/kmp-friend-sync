package org.joseph.friendsync.data.local

import app.cash.sqldelight.db.SqlDriver
import org.joseph.friendsync.PlatformConfiguration

expect class DatabaseDriverFactory constructor(platformConfiguration: PlatformConfiguration) {

    fun createDriver(): SqlDriver
}