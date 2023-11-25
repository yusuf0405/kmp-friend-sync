package org.joseph.friendsync.data.cache

import app.cash.sqldelight.db.SqlDriver
import org.joseph.friendsync.PlatformConfiguration

actual class DatabaseDriverFactory actual constructor(platformConfiguration: PlatformConfiguration) {

    actual fun createDriver(): SqlDriver {
        //        return NativeSqliteDriver(AppDatabase.Schema, "app.db")
        TODO("Not yet implemented")
    }
}